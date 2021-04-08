FROM node:lts-alpine

# static 컨텐츠를 위한 http server를 설치한다.
RUN npm install -g http-server

# /app이라는 폴더를 만들고 이를 working directory로 지정한다.
WORKDIR /app

# package.json과 package-lock.json 파일을 복사한다.
COPY package*.json ./

# 필요한 패키지들을 설치한다.
RUN npm install

# 위에서 만든 working directory에 프로젝트 파일과 폴더들을 복사한다.
COPY . .

# 빌드를 실행한다.
RUN npm run build

# 80 포트로 돌아가게끔 한다.
EXPOSE 80
CMD [ "http-server", "-p 80", "dist" ]