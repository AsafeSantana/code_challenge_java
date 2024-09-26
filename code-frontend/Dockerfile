# Use uma imagem base do Node.js
FROM node:14

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o package.json e o package-lock.json para o contêiner
COPY package*.json ./

# Instala as dependências da aplicação
RUN npm install

# Copia o restante da aplicação para o contêiner
COPY . .

# Compila a aplicação para produção
RUN npm run build

# Instala um servidor para servir os arquivos estáticos
FROM nginx:alpine

# Copia os arquivos de build para o diretório do NGINX
COPY --from=0 /app/build /usr/share/nginx/html

# Exponha a porta 80
EXPOSE 80

# Comando padrão para executar o NGINX
CMD ["nginx", "-g", "daemon off;"]
