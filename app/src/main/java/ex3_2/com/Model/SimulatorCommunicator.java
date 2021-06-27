package ex3_2.com.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SimulatorCommunicator {
    private Client client;
    private final String aileron = "set /controls/flight/aileron ";
    private final String elevator = "set /controls/flight/elevator ";
    private final String rudder = "set /controls/flight/rudder ";
    private final String throttle = "set /controls/engines/current-engine/throttle ";

    public void setAileron(float f){
        client.SendMsg(aileron + Float.toString(f) + "\r\n");
    }
    public void setElevator(float f){
        client.SendMsg(elevator + Float.toString(f) + "\r\n");
    }
    public void setRudder(float f){
        client.SendMsg(rudder + Float.toString(f) + "\r\n");
    }
    public void setThrottle(float f){
        client.SendMsg(throttle + Float.toString(f) + "\r\n");
    }

    public String StartFlight(String address, String port) {
        System.out.println("trying to connect to the Flight Gear.\n");
        System.out.println(address + ", " + port + "\n");
        String s = "Connection to IP: " + address + ", port: " + port;
        client = new Client();
        try { client.SetConnection(address, Integer.parseInt(port)); }
        catch (IOException e) {
            return s + "\nConnection Failed.";
        }
        return s;
    }
    public void ChangePause(){
        client.SendMsg("run pause\r\n");
    }
    public void EndFlight(){
        //end FG command
        try { client.Close(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private class Client{
        private PrintWriter writer;
        Socket socket;

        public void SetConnection(String address, int port) throws IOException{
            socket = new Socket(address, port);
            System.out.println("Socket opened.\n");
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        }
        public void SendMsg(String cmd){
            writer.print(cmd);
            writer.flush();
        }
        public void Close() throws IOException {
            writer.close();
            socket.close();
        }
    }
}
