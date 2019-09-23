package priv.benchmarck.test;

import com.google.common.collect.Lists;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yuqiang lin
 * @description 简单基准测试
 * @email 1098387108@qq.com
 * @date 2019/9/20 5:08 PM
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1)
@Threads(1)
@State(Scope.Thread)
@Measurement(iterations = 1)
public class BasicBenchmarkTest {
    @Param({"10", "1000", "10000"})
    private int n;

    private List<Integer> arrayList;
    private List<Integer> linkedList;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BasicBenchmarkTest.class.getSimpleName())
                .jvmArgs("-Xms20m", "-Xmx20m")
                .forks(1)
                .build();

        new Runner(options).run();
    }

    @Setup(Level.Invocation)
    public void init() {
        arrayList = Lists.newArrayListWithCapacity(n);
        linkedList = Lists.newLinkedList();

        for (int i = 0; i < n; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
    }

    @TearDown(Level.Invocation)
    public void clear() {
        arrayList.clear();
        linkedList.clear();
    }

    @Benchmark
    public void removeFirstElementInArrayList() {
        while (!arrayList.isEmpty()) {
            arrayList.remove(0);
        }
    }

    @Benchmark
    public void removeFirstElementInLinkedList() {
        while (!linkedList.isEmpty()) {
            linkedList.remove(0);
        }
    }
}
