FROM node:17-alpine

ENV HOST='0.0.0.0'
ENV PORT='3000'

WORKDIR /webapp

COPY ./ /webapp

RUN npm install
RUN npm run build

EXPOSE 3000

CMD [ "npm", "run", "start" ]