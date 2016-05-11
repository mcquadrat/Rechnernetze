package simon;

public interface ClientCallbackInterface {
	 /**
     * server tells the client which move the ki did
     * 
     * @param move
     *            array with to elements, first is row of ki-move, second is
     *            column of ki-move
     */
    void callback(int[] move);
}
