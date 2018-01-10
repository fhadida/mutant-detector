# Mutant Detector

**Mutant Detector** es una API para detectar si un ADN es Mutante o no. Además, incluye un servicio (`/stats`) para conocer las estadísticas de los ADNs ingresados.

## Modo de Uso

### Servicio para saber si un ADN es mutante o no.

* **URL**
  
  `/mutant`
  
* **Method**

  `POST`
  
* **Body Request**

  ***Requerido***

  *JSON Schema:*
	
  ```json
  {
    "description": "Representación de la cadena de ADN",
    "type": "object",
    "required": ["dna"],
    "properties": {
      "dna": {
        "type": "array",
        "items": "string"
      } 
    }
  }
  ```

  *Ejemplo:*
  
  ```json
  {
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }
  ```


* **Respuestas**

    * Http Code: `200`    *- si el ADN es mutante*
    * Http Code: `403`    *- en cualquier otro caso*


### Servicio para consultar estadísticas

* **URL**
  
  `/stats`
  
* **Method**

  `GET`
  
* **Respuesta**

    * Http Code: `200`
    
      Content: 
      
      ```json
      {
        "description": "Representación de las estadisticas",
        "type": "object",
        "properties": {
          "count_mutant_dna": { "type": "number" },
          "count_human_dna": { "type": "number" },
          "ratio": { "type": "number" }
        }
      }
      ```
     
      *Ejemplo:*
  
      ```json
      {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
      }
      ```
     
## URL

  <https://mutants-detector.appspot.com/>
