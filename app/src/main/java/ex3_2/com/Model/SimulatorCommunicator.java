package ex3_2.com.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
        client.shouldPause = true;
        client.SendCommand("run pause\r\n");
    }
    public void EndFlight(){
        client.SendCommand("run reset\r\n");
        client.shouldFly = false;
        client.shouldPause = true;
    }

    private class Client{
        public Map<String, Float> map;
        public boolean shouldFly = true;
        public boolean shouldPause = false;
        private PrintWriter writer;
        private OutputStream out;

        public void SendCommand(String cmd){
            writer.print(cmd + "\r\n");
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
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
                    writer.print(CreateAttributesString());
                    try { out.flush(); }
                    catch (IOException e) { e.printStackTrace(); }
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
                Communicate();
                out.close();
                s.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
