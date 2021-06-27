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

import static java.lang.Thread.sleep;

public class SimulatorCommunicator {
    private Client client;
    public SimulatorCommunicator(){
        client = new Client();
        client.map = new LinkedHashMap<>();
    }
    public void AddAttribute(String name, String initialValue){
        client.map.put(name, Float.valueOf(initialValue).floatValue());
    }
    public void SetAttribute(String attr, float value){ client.map.put(attr, value); }
    public void StartFlight(String address, String port) {
        System.out.println("trying to connect to the Flight Gear.\n");
        System.out.println(address + ", " + port + "\n");
        client.StartFlight(address, Integer.parseInt(port));
    }
    public void ChangePause(){
        //client.shouldPause = true;
        if (client.flight_started)
            client.SendMsg("run pause\r\n");
    }
    public void EndFlight(){
        //end FG command
        client.shouldFly = false;
        client.shouldPause = true;
    }

    private class Client{
        public Map<String, Float> map;
        public boolean shouldFly = true;
        public boolean shouldPause = false;
        public boolean flight_started = false;
        private PrintWriter writer;
        private OutputStream out;

        public void SendMsg(String cmd){
            writer.print(cmd);
            try { out.flush(); }
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
                    try { sleep(100); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                try { sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            writer.close();
        }
        private void ErrorMsg(String msg){

        }
        public void StartFlight(String address, int port) {
            System.out.println("In the inner class.\n");
            try {
                Socket s = new Socket(address, port);
                System.out.println("Socket opened.\n");
                this.out = s.getOutputStream();
                System.out.println("Output stream opened.\n");
                this.writer = new PrintWriter(out, true);
                client.flight_started = true;
                Communicate();
                out.close();
                s.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }private class UDPClient{
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
}
