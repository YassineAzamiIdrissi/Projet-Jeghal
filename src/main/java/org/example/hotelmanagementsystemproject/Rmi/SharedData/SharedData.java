package org.example.hotelmanagementsystemproject.Rmi.SharedData;

public class SharedData {
    private static int roomId;

    public static void setRoomId(int id) {
        roomId = id;
    }

    public static int getRoomId() {
        return roomId;
    }

}
