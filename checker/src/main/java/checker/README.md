Un user (student) are o grupa si o lista de materii
-> am adaugat metoda pentru a seta o materie unei intregi grupe


Taskurile pot fi pentru mai multe grupe la o materie si vor avea un set de rezultate.
->Ca sa leg un task de un user, pot obtine doar o lista de taskuri aferente materiei si grupei, upa care va trebui sa caut dupa id (sper ca se poate face ceva in interfata astfel incat atunci cand am o lista de taskuri si selectez unul, sa se faca un request dupa id)

Un rezultat apartine unui user si unui task. Cand un user va face submit la sursa se va face un rezultat, care va fi updatat atunci cand se va primi rezultatul dupa corectare.
Atunci cand cream un rezultat nou pentru un user, acesta trebuie sa fie asociat cu un task, chestia asta se poate face doar dupa id-u taskului

->in DbService din publisherii de kafka ar trebui facut un PUT request cu metoda de updateResultWithResultLink cu un obiect de tip Result care sa contina link-ul sursa  


Flow: userii si taskurile deja create; eu cand creez un rezultat trebuie sa am informatie si despre task si despre user (ResultDTO)
Un rezultat este creat dupa ce un user face upload si primeste de la minio un link pentru arhiva sursa incarcata
dupa ce creez un rezultat care e asignat unui task si unui user, urmeaza ca acesta sa fie updatat cu link-ul catre rezultat primit tot de la minio(un fisier txt eventual) si salvat in aza de date de catre publishul kafka (acesta apeleaza prin WebClient conrollerul updateResultWithResultLink)

testare 0.0 
in Postman un post request catre http://127.0.0.1:8090/results/1 (userul cu id-ul 1) cu body-ul
{
"sourceLink": "https://play.min.io/cristianabucket/uploaddetest",
"taskId":1
}
apoi un PUT request catre http://127.0.0.1:8090/results
{
"sourceLink": "https://play.min.io/cristianabucket/uploaddetest",
"resultLink":"https://play.min.io/cristianabucket/uploaddetestcurezultat",
"taskId":1
}