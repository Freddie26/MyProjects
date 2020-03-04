package WebSocket;

public class SequenceLengthMessage {

    private int length;

    public SequenceLengthMessage() {
    }

    public SequenceLengthMessage(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
