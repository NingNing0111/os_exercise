在具备了执行用户态进程的能力之后，uCore 要为这些进程提供的一个重要服务，是用户进程之间的消息传递机制（Inter-ProcessCommunication，简写为 IPC）。现在，我们要为 uCore 实现以下两个系统调用，以实现一种同步的 IPC 机制（暂不考虑超时等功能）：
`int sys_send_event(int pid, int event)`;

- 参数：pid - 该消息的目标进程的进程号；

- event – 消息内容，用一个整型表示。

- 返回值：消息成功发送时，返回 0；否则，返回相应的错误代码。

`int sys_recv_event(int pid, int event)`;

- pid - 函数返回时，pid 保存发出消息的进程的进程号，可以为 NULL；
- event – 函数返回时，event 保存消息内容，可以为 NULL。

&emsp;&emsp;返回值：消息成功接收时，返回 0；否则，返回相应的错误代码。

1）以下是一个基于上述 IPC 机制求质数的用户程序：

```C
#include <stdio.h>

const int total = 1000;
void primeproc(void)
{
    int index = 0, this, num, pid = 0;
top:
    recv_event(NULL, &this;);
    cprintf("%d is a primer.", this);
    while (recv_event(NULL, &num;) == 0)
    {
        if ((num % this) == 0)
        {
            continue;
        }
        if (pid == 0)
        {
            if (index + 1 == total)
            {
                goto out;
            }
            if ((pid = fork()) == 0)
            {
                index++;
                goto top;
            }
            if (pid < 0)
            {
                goto out;
            }
        }
        if (send_event(pid, num) != 0)
        {
            goto out;
        }
    }
out:
    cprintf("[%04d] %d quit.", getpid(), index);
}
int main(void)
{
    int i, pid;
    unsigned int time = gettime_msec();
    if ((pid = fork()) == 0)
    {
        primeproc();
        exit(0);
    }
    assert(pid > 0);
    for (i = 2;; i++)
    {
        if (send_event(pid, i) != 0)
        {
            break;
        }
    }
    cprintf("use %d msecs.", gettime_msec() - time);
    cprintf("primer3 pass.");
    return 0;
}

```

&emsp;&emsp;简述这个程序是如何判断并输出前五个质数的。

2）给出一种基于等待队列的上述 IPC 机制的实现方案。
