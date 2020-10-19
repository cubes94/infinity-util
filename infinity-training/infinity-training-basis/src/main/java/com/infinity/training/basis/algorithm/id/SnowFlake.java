package com.infinity.training.basis.algorithm.id;

/**
 * 雪花算法
 * <p>
 * 分布式ID固定是一个long型的数字，一个long型占8个字节，也就是64个bit，原始雪花算法中对于bit的分配为：
 * 第一个bit位是标识部分，java中由于long的最高位是符号位，一般生成的ID为正数，所以固定为0；
 * 时间戳部分占41bit，一般存储的是与一个预设时间的差值，41位的时间戳可以使用69年，(1L<<41)/(1000L*60*60*24*365) = 69年；
 * 工作机器ID占10bit，这里比较灵活，比如可以用前5位作为数据中心机房标识，后5位作为单机房机器标识，可以部署1024个节点；
 * 序列号部分占12bit，支持同一毫秒内同一个节点可以生成4096个ID。
 *
 * @author whc
 * @version 1.0.0
 * @since 2020/10/11 19:53
 */
public class SnowFlake {

    /**
     * 预设起始时间戳
     */
    private static final long START_TIMESTAMP = 1602417950312L;

    /**
     * 每部分占用的位数
     */
    private static final long DATA_CENTER_BIT = 5; // 数据中心占用位数
    private static final long MACHINE_BIT = 5; // 机器标识占用位数
    private static final long SEQUENCE_BIT = 12; // 序列号占用位数

    /**
     * 每部分最大值
     */
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1 << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1 << SEQUENCE_BIT);

    /**
     * 每部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATA_CENTER_LEFT = MACHINE_LEFT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long dataCenterId; // 数据中心ID

    private final long machineId; // 机器ID

    private long sequenceNo; // 序列号

    private long lastTimeStamp = -1L; // 上一次时间戳

    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("wrong dataCenterId");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("wrong mechineId");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 获取下一个ID
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        long currentTimeStamp = currentTimeStamp();
        if (currentTimeStamp < START_TIMESTAMP) {
            throw new RuntimeException("time too early");
        }
        if (currentTimeStamp == lastTimeStamp) {
            sequenceNo = (sequenceNo + 1) & MAX_SEQUENCE;
            if (sequenceNo == 0L) {
                currentTimeStamp = waitNextTimeStamp();
            }
        } else {
            sequenceNo = 0L;
        }
        lastTimeStamp = currentTimeStamp;
        return (currentTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequenceNo;
    }

    /**
     * 当前时间戳
     */
    private long currentTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 等待到下一毫秒时间戳
     */
    private long waitNextTimeStamp() {
        long currentTimeStamp = currentTimeStamp();
        while (currentTimeStamp == lastTimeStamp) {
            currentTimeStamp = currentTimeStamp();
        }
        return currentTimeStamp;
    }
}
