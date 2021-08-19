pipeline {
	agent none
	options { skipDefaultCheckout(true) }

	stages {
		stage('Build and Test') {
			agent {
				docker {
					image 'gradle:6.7.1-jdk8-openj9'
                    			args '-v /root/.gradle:/root/.gradle'
				}
			}
			options { skipDefaultCheckout(false) }
			steps {
				sh 'gradle build'
			}
		}
		stage('Docker build') {
			agent any
			steps {
				sh 'docker build -t inline_backend:latest /Backend/Dockerfile'
				sh 'docker build -t inline_RTC:latest /groupcall_test/Dockerfile'
				sh 'docker build -t inline_frontend:latest /Frontend/Dockerfile'
			}
		}
		stage('Docker run') {
			agent any
			steps {
				// 현재 동작중인 컨테이너 중 <front-image-name>의 이름을 가진
				// 컨테이너를 stop 한다
				sh 'docker ps -f name=inline_backend -q \
					| xargs --no-run-if-empty docker container stop'
				// 현재 동작중인 컨테이너 중 <back-image-name>의 이름을 가진
				// 컨테이너를 stop 한다
				sh 'docker ps -f name=inline_RTC -q \
					| xargs --no-run-if-empty docker container stop'
				sh 'docker ps -f name=inline_frontend -q \
                                        | xargs --no-run-if-empty docker container stop'

				// <front-image-name>의 이름을 가진 컨테이너를 삭제한다.
				sh 'docker container ls -a -f name=inline_backend -q \
					| xargs -r docker container rm'
				// <back-image-name>의 이름을 가진 컨테이너를 삭제한다.
				sh 'docker container ls -a -f name=inline_RTC -q \
					| xargs -r docker container rm'
				sh 'docker container ls -a -f name=inline_frontend -q \
                                        | xargs -r docker container rm'

				// docker image build 시 기존에 존재하던 이미지는
				// dangling 상태가 되기 때문에 이미지를 일괄 삭제
				sh 'docker images -f "dangling=true" -q \
					| xargs -r docker rmi'
				// docker container 실행
				sh 'docker run -d --name inline_backend -p 8080:8080 inline_backend:latest'
				sh 'docker run -d --name inline_RTC -p 8995:8998 inline_RTC:latest'
				sh 'docker run -d --name inline_frontend -p 7070:80 inline_front:latest'
			}
		}
	}
}
