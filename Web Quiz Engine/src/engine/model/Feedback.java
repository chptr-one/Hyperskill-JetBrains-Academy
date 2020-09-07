package engine.model;

@SuppressWarnings("unused")

public class Feedback {

    public final static Feedback SUCCESS = new Feedback(true, "Congratulations, you're right!");
    public final static Feedback FAILURE = new Feedback(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    public Feedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}

