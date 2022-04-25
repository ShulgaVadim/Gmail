package models;

public class Email {

    private String subject;
    private String addressee;
    private String message;

    public Email(String subject, String addressee, String message) {
        this.subject = subject;
        this.addressee = addressee;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getMessage() {
        return message;
    }
}

