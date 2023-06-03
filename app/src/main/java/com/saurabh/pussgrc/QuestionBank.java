package com.saurabh.pussgrc;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private List<Question> list = new ArrayList<>();

    public QuestionBank() {
        list.add(new Question("Do you feel energized after spending time with a large group of people or after spending time alone or with a small group of close friends?", new int[]{1, 0, 0, 0}));
        list.add(new Question("Do you enjoy social events and parties or prefer quieter, more intimate gatherings?", new int[]{1, 0, 0, 0}));
        list.add(new Question("Do you tend to think out loud and process information by talking or prefer reflecting quietly and thinking before speaking?", new int[]{1, 0, 0, 0}));
        list.add(new Question("Do you enjoy being the center of attention and feel comfortable in social settings or prefer observing and listening in social situations?", new int[]{1, 0, 0, 0}));
        list.add(new Question("Do you often seek out opportunities for social interaction and enjoy group activities or prefer spending time alone or with a few close friends?", new int[]{1, 0, 0, 0}));

        list.add(new Question("Do you focus on concrete details and facts or often find yourself thinking about abstract concepts and possibilities?", new int[]{0, 1, 0, 0}));
        list.add(new Question("Are you observant and attentive to your immediate surroundings or often find your mind wandering and thinking about future possibilities?", new int[]{0, 1, 0, 0}));
        list.add(new Question("Do you tend to rely on your five senses to gather information and trust tangible evidence or trust your gut instincts and rely on patterns and connections?", new int[]{0, 1, 0, 0}));
        list.add(new Question("Are you more interested in facts, data, and real-world experiences or enjoy exploring theories, possibilities, and potential outcomes?", new int[]{0, 1, 0, 0}));

        list.add(new Question("When making decisions, do you primarily rely on logical analysis and objective considerations or consider personal values and emotions?", new int[]{0, 0, 1, 0}));
        list.add(new Question("Are you more comfortable expressing your thoughts and ideas directly and objectively or prioritize maintaining harmony and considering the feelings of others?", new int[]{0, 0, 1, 0}));
        list.add(new Question("Do you tend to approach situations with a logical and rational mindset or take into account personal values and how people might be affected?", new int[]{0, 0, 1, 0}));
        list.add(new Question("When faced with a problem, do you tend to analyze it objectively and prioritize logical solutions or consider the impact on people involved and prioritize empathetic solutions?", new int[]{0, 0, 1, 0}));

        list.add(new Question("Do you prefer having a clear plan and structure in your daily life or prefer to keep your options open and be spontaneous?", new int[]{0, 0, 0, 1}));
        list.add(new Question("Are you more comfortable when things are planned and scheduled in advance or enjoy being spontaneous and flexible with your time?", new int[]{0, 0, 0, 1}));
        list.add(new Question("Do you tend to work steadily toward goals, taking a systematic approach or prefer to explore multiple options and adapt as you go?", new int[]{0, 0, 0, 1}));
    }

    public List<Question> getList() {
        return list;
    }

}
