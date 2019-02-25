import socket
import time
import sys
#填写服务器的ip和端口
SERVER_IP = ""
SERVER_PORT = 

print("Starting socket: TCP...")
server_addr = (SERVER_IP, SERVER_PORT)
#创建基于IPv4和TCP的Socket
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

while True:
    try:
        print("Connecting to server @ %s:%d..." %(SERVER_IP, SERVER_PORT))
        # 建立连接:
        socket_tcp.connect(server_addr)
        #接收欢迎信息
        print(socket_tcp.recv(1024).decode('utf-8'))
        break
    except Exception:
        print("Can't connect to server,try it latter!")
        time.sleep(1)
        continue

def sendMsg():
    if sys.argv[1]=='1':
        socket_tcp.send(b'open')

#发送信息
sendMsg()

while True:
    data = socket_tcp.recv(1024)
    #成功接收到发送回来的信息时断开连接，否则重复发送
    if data:
        print("Received: %s" % data.decode('utf-8'))
        socket_tcp.send(b'exit')
        break
    else:
        sendMsg()

socket_tcp.close()
sys.exit(1)