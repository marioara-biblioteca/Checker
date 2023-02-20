package kakfka_demo.db;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DbService {

    public void saveResultToDb(String sourceLink,String resultLink){

        Result result=getResult(sourceLink);
        if(result!=null){
            result.setResultLink(resultLink);
            WebClient.Builder builder = WebClient.builder();
            //metoda din checker returneaza un ResultDTO
            Result newRes = builder.build()
                    //apeleaza updateResult cu resultLink-ul
                    .put()
                    .uri("http://localhost:8080/results")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(result), Result.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Result.class)
                    .block();
            System.out.println("UpdatedResult!");
        }
    }
   private Result getResult(String sourceLink){
       WebClient.Builder builder =WebClient.builder();
       //metoda din checker returneaza o lista de resultDTO-uri
       List<Result> results =builder.build()
               .get()
               .uri("http://localhost:8080/results")
               .retrieve()
               .bodyToFlux(Result.class)
               .collectList()
               .block();
       for(Result result: results)
           if(result.getSourceLink().equals(sourceLink))
               return result;
       return null;
   }

}
