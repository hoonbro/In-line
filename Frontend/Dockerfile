FROM node:lts-alpine as build-stage
WORKDIR /hompage
COPY package*.json ./

RUN npm install
COPY . .
RUN npm run build
RUN ls
RUN cd ../
RUN ls

FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/homepage.conf /etc/nginx/conf.d/homepage.conf

COPY --from=build-stage /hompage/dist /usr/share/nginx/html/homepage
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
