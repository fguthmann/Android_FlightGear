package ex3_2.com.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

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
        try {
            client.Close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private class Client{
        private PrintWriter writer;
        private OutputStream out;
        Socket socket;

        public void SetConnection(String address, int port) throws IOException{
            socket = new Socket(address, port);
            System.out.println("Socket opened.\n");
            this.out = socket.getOutputStream();
            System.out.println("Output stream opened.\n");
            this.writer = new PrintWriter(out, true);
        }
        public void SendMsg(String cmd){
            writer.print(cmd);
            try { out.flush(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        public void Close() throws IOException {
            writer.close();
            out.close();
            socket.close();
        }
    }
    /**
     private class UDPClient{
        public Map<String, Float> map;
        public boolean shouldFly = true;
        public boolean shouldPause = false;
        private DatagramSocket socket;
        private InetAddress address;
        private int port;
        private byte[] buf;


        private void SendMsg(String msg){
            buf = msg.getBytes();
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 4445);
            try { socket.send(packet); }
            catch (IOException e) { e.printStackTrace(); }
        }

        private String CreateAttributesString(){
            String s = "";
            for (String name : map.keySet()) {
                s += "set /controls/" + name + " " + map.get(name).toString() + "\r\n";
            }
            return s;
        }

        private void Communicate(){
            System.out.println("Communication started.\n");
            while (shouldFly){
                while (!shouldPause) {
                    SendMsg(CreateAttributesString());
                }
                try { sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            socket.close();
        }

        public void StartFlight(String address, int port) {
            System.out.println("In the inner class.\n");
            try { this.address = InetAddress.getByName(address); }
            catch (UnknownHostException e) { e.printStackTrace(); }
            this.port = port;
            try {
                this.socket = new DatagramSocket();
                System.out.println("Socket opened.\n");
                System.out.println("Output stream opened.\n");
                Communicate();
            } catch (SocketException e) {
                e.printStackTrace();
            }

        }
    }

     private String CreateAttributesString(){
     String s = "";
     for (String name : map.keySet()) {
     s += "set /controls/" + name + " " + map.get(name).toString() + "\r\n";
     }
     return s;
     }

     private void Communicate(){
     System.out.println("Communication started.\n");
     while (shouldFly){
     while (!shouldPause) {
     SendMsg(CreateAttributesString());
     try { sleep(100); }
     catch (InterruptedException e) { e.printStackTrace(); }
     }
     try { sleep(1000); }
     catch (InterruptedException e) { e.printStackTrace(); }
     }
     }
    */
}
