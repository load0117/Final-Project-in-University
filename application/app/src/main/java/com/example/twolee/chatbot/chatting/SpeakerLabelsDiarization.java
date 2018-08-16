package com.example.twolee.chatbot.chatting;


import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeakerLabel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechTimestamp;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.util.GsonSingleton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;


public class SpeakerLabelsDiarization {
    /*
        private static CountDownLatch lock (맨 밑에 코드 존재)

        CountDownLatch
        하나 이상의 스레드가 다른 스레드에서 수행되는 일련의 작업이 완료 될 때까지 대기하도록하는 동기화 보조 도구입니다.
        출처 : https://developer.android.com/reference/java/util/concurrent/CountDownLatch

        뭐하는 클래스지?
    */

    /*
        내부 정적 클래스 1

        내부 정적 클래스로 만든 이유?
        바깥 클래스가 없어도 객체화 가능하다.

        사용 가능 메서드

            void updateFrom(SpeechTimestamp speechTimestamp)

            void updateFrom(SpeakerLabel speakerLabel)

     */
    public static class RecoToken {
        private Double startTime;
        private Double endTime;
        private Integer speaker;
        private String word;
        private Boolean spLabelIsFinal;

        /*
            생성자 파라미터 SpeechTimestamp 클래스
         */
        RecoToken(SpeechTimestamp speechTimestamp) {
            //시작 시간, 끝나는 시간, 말한 단어들
            startTime = speechTimestamp.getStartTime();
            endTime = speechTimestamp.getEndTime();
            word = speechTimestamp.getWord();
        }

        /*
            생성자 파라미터 SpeakerLabel 클래스
         */
        RecoToken(SpeakerLabel speakerLabel) {
            //보내는 사람, 받는 사람, 스피커
            startTime = speakerLabel.getFrom();
            endTime = speakerLabel.getTo();
            speaker = speakerLabel.getSpeaker();
        }

        /*
            단어 변경이나 추가.
         */
        void updateFrom(SpeechTimestamp speechTimestamp) {
            word = speechTimestamp.getWord();
        }

        /*
            스피커 변경
         */
        void updateFrom(SpeakerLabel speakerLabel) {
            speaker = speakerLabel.getSpeaker();
        }
    }

    /*
        내부 정적 클래스 2
        speaker 와 transcript 기록

        사용 가능 메서드

            None
     */
    public static class Utterance {
        private Integer speaker;
        private String transcript = ""; // what is transcript

        /*
            생성자 read only 숫자 speaker, 문자열 transcript
         */
        public Utterance(final Integer speaker, final String transcript) {
            this.speaker = speaker;
            this.transcript = transcript;
        }
    }

    /*
        내부 정적 클래스 3

        사용 가능 메소드 :

        public void add(SpeechResults speechResults)

        public void add(SpeechTimestamp speechTimestamp)

        public void add(SpeakerLabel speakerLabel)

        public void report()
     */
    public static class RecoTokens {

        // 시작 시간 , 토큰 기록
        private Map<Double, RecoToken> recoTokenMap;

        /*
            생성자 linkedHashMap 왜 사용 - 기록 하려고
         */
        public RecoTokens() {
            recoTokenMap = new LinkedHashMap<>();
        }

        /*
            음성 추가.
            SpeechResults waston 제공 클래스
         */
        public void add(SpeechResults speechResults) {
            // 음성이 존재하면
            if (speechResults.getResults() != null)
                for (int i = 0; i < speechResults.getResults().size(); i++) {
                    Transcript transcript = speechResults.getResults().get(i);
                    if (transcript.isFinal()) {
                        SpeechAlternative speechAlternative = transcript.getAlternatives().get(0);

                        for (int ts = 0; ts < speechAlternative.getTimestamps().size(); ts++) {
                            SpeechTimestamp speechTimestamp = speechAlternative.getTimestamps().get(ts);
                            add(speechTimestamp);
                        }
                    }
                }
            // 라벨 없으면
            if (speechResults.getSpeakerLabels() != null)
                for (int i = 0; i < speechResults.getSpeakerLabels().size(); i++) {
                    add(speechResults.getSpeakerLabels().get(i));
                }

        }

        /*
            시간 추가.
            SpeechTimestamp waston 제공 클래스
         */
        public void add(SpeechTimestamp speechTimestamp) {
            RecoToken recoToken = recoTokenMap.get(speechTimestamp.getStartTime());
            if (recoToken == null) {
                //녹화된 음성(?)이 아니거나 처음의 음성임면 인스턴스 생성
                recoToken = new RecoToken(speechTimestamp);
                recoTokenMap.put(speechTimestamp.getStartTime(), recoToken);
            } else {
                //기존의 존재한 것이면 새로운 단어로 업데이트
                recoToken.updateFrom(speechTimestamp);
            }
        }

        /*
            라벨 추가

         */
        public void add(SpeakerLabel speakerLabel) {
            RecoToken recoToken = recoTokenMap.get(speakerLabel.getFrom());
            if (recoToken == null) {
                //from이 없으면 새로운 인스턴스
                recoToken = new RecoToken(speakerLabel);
                recoTokenMap.put(speakerLabel.getFrom(), recoToken);
            } else {
                //아니면 speaker 변경
                recoToken.updateFrom(speakerLabel);
            }

            if (speakerLabel.isFinal()) {
                //마지막이면 true 값으로 마크 (markTokensBeforeAsFinal) , 보고 후 마지막 제거.
                markTokensBeforeAsFinal(speakerLabel.getFrom());
                report();
                cleanFinal();
            }
        }


        // 마지막 기록
        private void markTokensBeforeAsFinal(Double from) {
            Map<Double, RecoToken> recoTokenMap = new LinkedHashMap<>();

            for (RecoToken rt : recoTokenMap.values()) {
                if (rt.startTime <= from)
                    rt.spLabelIsFinal = true;
            }
        }

        /*
            기록
         */
        public void report() {
            List<Utterance> uttterances = new ArrayList<>();
            Utterance currentUtterance = new Utterance(0, "");

            for (RecoToken rt : recoTokenMap.values()) {
                if (currentUtterance.speaker != rt.speaker) {
                    uttterances.add(currentUtterance);
                    currentUtterance = new Utterance(rt.speaker, "");
                }
                currentUtterance.transcript = currentUtterance.transcript + rt.word + " ";
            }
            uttterances.add(currentUtterance);

            String result = GsonSingleton.getGson().toJson(uttterances);
            System.out.println(result);
        }

        //마지막 찾아서 제거
        private void cleanFinal() {
            Set<Map.Entry<Double, RecoToken>> set = recoTokenMap.entrySet();
            for (Map.Entry<Double, RecoToken> e : set) {
                if (e.getValue().spLabelIsFinal) {
                    recoTokenMap.remove(e.getKey());
                }
            }
        }

    }


    private static CountDownLatch lock = new CountDownLatch(1);
}
