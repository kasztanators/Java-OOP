package pl.edu.pg.eti.ksg.op.javka;

public class Comment {
    private static String text = "";

    public static void AddComment(String newComment) {
        text += newComment + "\n";
    }

    public static String getText() {
        return text;
    }

    public static void ResetComment() {
        text = "";
    }
}
