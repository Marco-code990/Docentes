package ipn.docentes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WakeonLan {
    public static void sendWakeOnLan(String ipAddress, String macAddress) throws Exception {
        byte[] magicPacket = createMagicPacket(macAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, address, 9);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
        }
    }

    private static byte[] createMagicPacket(String macAddress) {
        try {
            byte[] macBytes = getMacBytes(macAddress);
            byte[] magicPacket = new byte[102];

            // Llenar los primeros 6 bytes con 0xFF
            for (int i = 0; i < 6; i++) {
                magicPacket[i] = (byte) 0xFF;
            }

            // Repetir la dirección MAC 16 veces
            for (int i = 6; i < magicPacket.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, magicPacket, i, macBytes.length);
            }

            return magicPacket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getMacBytes(String macAddress) throws Exception {
        byte[] bytes = new byte[6];
        String[] hex = macAddress.split("(\\:|\\-)");

        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }

        return bytes;
    }
}
