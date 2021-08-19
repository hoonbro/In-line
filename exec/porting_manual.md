# Inline-포팅 메뉴얼



- 포팅 전, 환경 변수 설정

```bash
export DOMAIN_NAME=<도메인 이름>
export IPV_AD=<퍼블릭 아이피 주소>
```

### 1. 설치

- apt를 업데이트

```bash
sudo apt-get update
sudo apt-get upgrade -y
```



#### 1) Git(latest) 설치

```bash
sudo apt install git
```



#### 2) Docker(20.10.8)와 Docker-Compose(1.25.0-rc2)  설치

- Docker(20.10.8) 설치

```bash
sudo apt update -y && apt upgrade -y

sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository \
"deb [arch=amd64] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) \
stable"

sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io
docker -v

sudo systemctl enable docker && service docker start
```

- Docker-Compose(1.25.0-rc2) 설치

```bash
sudo curl -L "https://github.com/docker/compose/releases/download/1.25.0-rc2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose

sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```



#### 3) Mysql (8.0.26-0ubuntu0.20.04.2)설치 및 설정

- Mysql(8.0.26-0ubuntu0.20.04.2) 설치

```bash
sudo apt-get install mysql-server
```

- Default Port인 3306 개방

```bash
sudo ufw allow 3306
```

- Mysql 실행

```bash
sudo systemctl start mysql
```

- Mysql 보안설정 실행

```bash
sudo mysql_secure_installaton
```

- 보안설정을 실행하면 다음과 같은 질문이 있음. 메뉴얼대로 답변
  - 보안이 강한 패스워드를 생성하기 위한 플러그인 활성화 : N
  - 패스워드의 레벨: 0
  - root 계정의 비밀번호 입력, 재확인 비밀번호 입력: ssafy
  - 입력한 비밀번호로 진행할지 : Y
  - 익명의 사용자를 제거: Y
  - root 계정으로 외부에서 접속할 것인지: N
  - 테스트 데이터베이스를 삭제할 것인지 : Y
  - pribileges table을 다시 로드 할 것인지 : Y
- 서버 접속

```bash
sudo mysql -u  root -p
```

- ssafy 계정생성 (root 계정은 내부에서만 사용할 것)

```sql
use mysql;
create user 'ssafy'@'%' identified by 'ssafy';
grant all privileges on *.* to 'ssafy'@'%'
flush privileges;
```



#### 4) Nginx(1.18.0) 설치

```bash
sudo apt-get install nginx
```

- Nginx 실행

```bash
sudo systemctl start nginx
```





### 2. Git lab 레퍼지토리 불러오기

- 원하는 장소에서 다음 명령어 실행 및 이동

```bash
sudo git clone <gitlab 주소>
cd <repository 디렉토리 이름>
```



### 3. Dump file DB에 넣기

- Repository의 exec 디렉토리로 이동

```bash
cd exec
```

- Dump data mysql로 

```bash
sudo mysql -u root -p  inline.sql < db.sql
```



### 4. Nginx 설정하기

- db 디렉토리에서 nginx 디렉토리로 이동

```bash
cd ../nginx
```

- Nginx에서 수정해야될 파일은 /etc/nginx/sites-available/default
- 현재 레포지토리 Nginx에 있는 default 파일을 복사해서 붙여넣기

```bash
sudo cp -r default /etc/nginx/sites-available/
```

- defualt 파일을 다음과 같이 수정함(수정 필요 부분은 주석으로 설명)
- 아래 파일은 레퍼지토리 Nginx 디렉토리에 있다.

```nginx
server {
	listen 80 default_server;
	listen [::]:80 default_server;

    server_name $DOMAIN_NAME

	
	return 301 https://$server_name$request_uri;
	index index.html index.htm;
}

server {
	listen 443 ssl;
	listen [::]:443 ssl;
	
	server_name $DOMAIN_NAME

	ssl_certificate <pem파일 경로> # 수정
	ssl_certificate_key <pem파일 경로> # 수정
	
	root /home/ubuntu/release/Frontend/dist;
	index index.html;

	location / {
		#try_files $uri $uri/ /index.html;
		proxy_pass http://localhost:7070;
	}

	location /api/v1 {
		proxy_pass http://localhost:8080;
		proxy_redirect off;
		charset utf-8;

		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		proxy_set_header X-NginX-Proxy true;
	}
	location /api/v1/stomp {
		proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header Host $http_host;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "Upgrade";
                proxy_set_header Accept-Encoding "";		

	}
	location /images{
		alias <images경로>;
	}	

}
server {
	listen 8995 ssl;

        server_name $DOMAIN_NAME

        ssl_certificate <pem파일 경로> # 수정
		ssl_certificate_key <pem파일 경로> # 수정
    
        location / {
                proxy_pass http://localhost:8998/;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header Host $http_host;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "Upgrade";
                proxy_set_header Accept-Encoding "";
		proxy_read_timeout 1800s; 
        }

}

```



### 5. Kurento-media, Coturn 서버 Docker로 실행

- 컨테이너와 연결할 볼륨 생성

```bash
sudo docker volume create kurento
sudo docker volume create coturn
```

- Kurento 미디어 서버 실행

```bash
sudo docker run -d --name kms --network host -v kurento:/etc/kurento kurento/kurento-media-server:latest

sudo docker run -d --name coturn --network host --user root coturn/coturn --external-ip='$IPV_AD' --relay-ip='172.17.0.1'
```

- WebRtcEndpoint.conf.ini 파일 열기

```
sudo vi /var/lib/docker/volumes/kurento/_data/modules/kurento/WebRtcEndpoint.conf.ini
```

- WebRtcEndpoint.conf.ini 파일 수정

```sh
stunServerAddress=<퍼블릭 아이피 주소>
stunServerPort=3478
turnURL=ssafy:ssafy@<퍼블릭 아이피 주소>?transport=udp
```

- Kurento 컨테이너 재시작

```bash
sudo dokcer restart kms
```

- kms, coturn container 확인

```bash
sudo docker ps
```



### 6. Backend, RTC, Frontend Docker-Compose

- Gitlab 레퍼지토리 디렉토리로 이동

- groupcall-test/Dockerfile 실행

```bash
sudo vi groupcall-test/Dockerfile
```

- kms 서버 주소 수정

```dockerfile
FROM openjdk:8-jdk-alpine
#FROM ubuntu:20.04

ADD . ./data

WORKDIR ./data

RUN chmod +x gradlew
RUN ./gradlew bootJar
RUN echo $DOMAIN
ENV JAVA_OPTS=""

ENTRYPOINT java -Dkms.url=ws://<도메인 이름>:8888/kurento -jar /data/build/libs/demo-0.0.1-SNAPSHOT.jar
```

- Docker-Compose로 세개의 서버를 실행

```bash
cd ..
sudo docker-compose up -d
```





### 7. Images Directory

- 이미지 파일의 전체 크기가 크지 않은 관계로 따로 S2를 구성하지 않았다.
- 이미지 파일은 별도의 디렉토리에 보관되는데 현재는 /home/ubunutu/images 이다.
- 다른 저장 디렉토를 사용하고 싶다면 API-Server의 경로와 Nginx의 이미지 경로를 수정해줘야 한다.