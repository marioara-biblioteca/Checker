ca sa pornim containerul
docker build -t myangular .
docker run --name myangular -d -p 8888:80 myangular
trebuie sa ne asiguram ca in checker la dev cors etc e setat portul corect
