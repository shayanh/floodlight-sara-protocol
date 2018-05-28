package net.floodlightcontroller.learningswitch;

import org.projectfloodlight.openflow.types.OFPort;

import java.util.*;

public class SaraProtocol {
    private Map<InEntry, OutEntry> learned = new HashMap<>();
    private Map<Long, Set<OutEntry>> waitingRoom = new HashMap<>();

    private long currentSwitch;

    public boolean haveEntryFor(long id) {
        return waitingRoom.containsKey(id);
    }

    public void setCurrentSwitch(long currentSwitch) {
        // todo save ports of ex switch
        this.currentSwitch = currentSwitch;
    }

    public void makeEntryFor(long currentSwitchId) {
        waitingRoom.put(currentSwitchId,new HashSet<>());
        // todo
    }

    public void printLearned() {
        System.out.print("Current Switch: ");
        System.out.println(currentSwitch);
        System.out.print("Learned: ");
        for (InEntry curr : learned.keySet()) {
            System.out.print(curr.getSw());
            System.out.print(" - ");
            System.out.print(curr.getPort());
            System.out.print(" || ");
            System.out.print(learned.get(curr).getSw());
            System.out.print(" - ");
            System.out.println(learned.get(curr).getPort());
        }
        System.out.println();
        System.out.print("WaitingRoom: ");
        for (Long curr : waitingRoom.keySet()) {
            System.out.print(curr);
            System.out.print(" || ");
            for (OutEntry c : waitingRoom.get(curr)) {
                System.out.print(c.getSw());
                System.out.print(" - ");
                System.out.print(c.getPort());
                System.out.print(" || ");
            }
            System.out.println();
        }
    }

    private class Entry {
        private long sw;
        private OFPort port;
        private long value;

        Entry(long sw, OFPort port, long value) {
            this.sw = sw;
            this.port = port;
            this.value = value;
        }

        public OFPort getPort() {
            return port;
        }

        public long getSw() {
            return sw;
        }

        public long getValue() {
            return value;
        }
    }

    private class InEntry extends Entry {
        InEntry(long sw, OFPort port, long value) {
            super(sw, port, value);
        }

        InEntry(OutEntry outEntry, long value) {
            super(outEntry.getSw(),outEntry.getPort(), value);
        }
    }

    private class OutEntry extends Entry {
        OutEntry(long sw, OFPort port, long value) {
            super(sw, port, value);
        }
    }

    public void learnLinkForCurrentSwitch(long sw, OFPort inPort, long value) {
        System.out.print("Switch in learnLinkForCurrentSwitch: ");
        System.out.println(sw);
        if (learned.containsKey(new InEntry(sw, inPort,value)))
            return;
        else if (waitingRoom.containsKey(sw)) {
            for( OutEntry current : waitingRoom.get(sw)){
                if(current.getSw() == currentSwitch){
                    learned.put(new InEntry(sw,inPort,value),current);
                    learned.put(new InEntry(current,value),new OutEntry(sw,inPort,value));
                    return;
                }
            }
            waitingRoom.get(currentSwitch).add(new OutEntry(sw, inPort,value));
        }else {
           // Set<OutEntry> temp = new HashSet<>();
           // temp.add(new OutEntry(sw, inPort,value));
            waitingRoom.get(currentSwitch).add(new OutEntry(sw, inPort,value));
        }
    }

}
