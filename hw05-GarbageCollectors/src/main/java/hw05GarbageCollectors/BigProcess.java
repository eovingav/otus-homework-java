package hw05GarbageCollectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BigProcess {

    private static Logger logger = LoggerFactory.getLogger(BigProcess.class);
    private ArrayList<byte[]> dataList = new ArrayList<>();
    private int itemAddCount = 4;
    private long totalRunsCount =0;

    public BigProcess(int itemAddCount) {
        this.itemAddCount = itemAddCount;
    }

    public BigProcess() {
    }

    public void run() {
        while (true) {
            addItems();
            deleteItems();
            totalRunsCount++;
            logger.info("total runs count:" + totalRunsCount);
        }
    }

    private void addItems() {
        for (int i = 0; i <= itemAddCount; i++){
            byte[] data = new byte[1];
            dataList.add(data);
        }
    }

    private void deleteItems() {
        int itemDeleteCount = (int) (itemAddCount - itemAddCount % 2) / 2;
        int itemIndex = dataList.size() - 1;
        for (int i = 1; i>=itemDeleteCount; i++) {
            dataList.remove(itemIndex);
            itemIndex--;
        }
    }
}
