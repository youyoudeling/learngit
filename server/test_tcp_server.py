import RPi.GPIO as GPIO
import time
import threading
import sys
import socket
#填写ip和端口
HOST_IP=""
HOST_PORT=
print("starting socket: TCP ...")
#创建基于IPv4和TCP的Socket
socket_tcp=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#绑定监听的端口
host_addr = (HOST_IP, HOST_PORT)
socket_tcp.bind(host_addr)
#调用listen来监听端口，参数为等待连接的最大数量
socket_tcp.listen(5)

def tcplink(sock, addr):
    print('Accept new connection from %s:%s...' % addr)
    socket_con.send(b'Welcome!')
    while True:
        data = sock.recv(1024)
        time.sleep(1)
        if not data or data.decode('utf-8') == 'exit':
            break
        elif data.decode('utf-8')=='open':
            gogo()
            time.sleep(0.1)
            stop()
        socket_con.send(('%s finished!'%data).encode('utf-8'))
    socket_con.close()
    print('Connection from %s:%s closed.' % addr)


#树莓派GPIO操作相关
GPIO.setmode(GPIO.BCM)

ENA=18
IN1=3
IN2=4

GPIO.setup(ENA,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(IN1,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(IN2,GPIO.OUT,initial=GPIO.LOW)

GPIO.output(ENA,True)

####定义电机正转函数
def gogo():
    print('motor gogo')
    #GPIO.output(ENA,True)
    GPIO.output(IN1,True)
    GPIO.output(IN2,False)

###定义电机反转函数
def back():
    print('motor_back')
    #GPIO.output(ENA,True)
    GPIO.output(IN1,False)
    GPIO.output(IN2,True)

###定义电机停止函数
def stop():
    print('motor_stop')
    #GPIO.output(ENA,False)
    GPIO.output(IN1,False)
    GPIO.output(IN2,False)


while True:
    # 接受一个新连接:
    socket_con, addr = socket_tcp.accept()
    # 创建新线程来处理TCP连接:
    t = threading.Thread(target=tcplink, args=(socket_con, addr))
    t.start()