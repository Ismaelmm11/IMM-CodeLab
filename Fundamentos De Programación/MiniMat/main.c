/**
 * Programa Hecho Por Ismael Mohamed
 * MiniMat: Calculadora de matrices
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include <windows.h>
#include "lib.h"
 
int main(){

    //Declarar variables a usar:

    int fin = 1;            //Entero para detener el bucle.

    TVars lista;            //Lista de variables
    lista.numVars = 0;
    lista.primera = NULL;

    TMatriz *nuevaMatriz;
    TMatriz *matrizAux;
    TMatriz *matrizAux2;
    TVar *nuevaVariable;

    //Vector con los comandos del programa
    char *palabrasABuscar[] = {"quit", "det", "product", "save", "load", "view", "over", "transp", "help"};
    //Vector con los operadores del programa
    char *signosABuscar[] = {"=", "+", "-", "*", "$", "&"};
    char *operador;

    char entrada[1000];     //Cadena para la entrada del usuario.

    char nomVar1[16];
    char nomArchiu[1000];

    char *token;
    char *tokenAux;

    int nIguales;
    int nPalabras = sizeof(palabrasABuscar) / sizeof(palabrasABuscar[0]);

    double escalar;
    double resultado;

    printf("\nPrograma realizado por: \n");
    printf("Ismael Mohamed Mohamed\n");
    printf("ismael.mohamed@goumh.umh.es\n\n");
    printf("Para saber sobre el funcionamiento del programa puede usar el comando 'help'.\n");

    //En caso de error de un comando siempre se liberará la memoria correspondiente.
    while (fin == 1){           
        token = NULL;
        tokenAux = NULL;

        nuevaMatriz = NULL;
        matrizAux = NULL;
        matrizAux2 = NULL;

        nuevaVariable = NULL;

        TextColor(WHITE);
        printf("\n\n:[%i]> ", lista.numVars);
        fgets(entrada, sizeof(entrada), stdin);

        eliminarSaltoLinea(entrada);
        Trim(entrada);

        nIguales = comprobarIguales(entrada);

        switch (nIguales){
            case 0:             //Imprimir matrices:
                //Comando quit:
                if(buscarPalabra(entrada, palabrasABuscar[0]) == 1){

                    if(strcmp(entrada, palabrasABuscar[0]) != 0){   //El comando quit debe ir sin parámetros.
                        TextColor(RED);
                        printf("\nERROR: Comando incorrecto, asegurese de escribirlo bien.\n");
                        break;
                    }

                    fin = 0;
                    break;
                }
                //Comando det:
                else if (buscarPalabra(entrada, palabrasABuscar[1]) == 1){

                    token = strtok(entrada, "\t ");
                    if(strcmp(token, palabrasABuscar[1]) != 0){     //El comando det debe empezar por "det ...".
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando det.\nAsegurese de escribirlo siguiendo el formato: 'det matriz'.\n");
                        break;
                    }

                    token = strtok(NULL, "");                       //Obtener el resto de la cadena que hay tras det.
                    if(token == NULL){                              //Si el resto de la cadena es NULO -> ERROR
                        TextColor(RED);
                        printf("\nERROR: Comando det incorrecto.\nFalta informacion en el comando.\n");
                        break;
                    }

                    Trim(token);                                    
                    matrizAux = VarOMatliberarMemoria(token, &lista);   //Ver si es matriz o variable.
                    if(matrizAux == NULL){
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando det.\nNo ha sido posible leer la matriz explicita o la variable.\n");
                        break;
                    }
                    else if(es_MatrizCuadrada(matrizAux) == 0){         //Verificar que la matriz sea cuadrada.
                        if(matrizAux->liberar == 1){
                            liberarMat(matrizAux);
                            matrizAux = NULL;
                        }
                        TextColor(RED);
                        printf("\nERROR: No es posible calcular el determinante de una matriz que no es cuadrada.\n");
                        break;
                    }

                    resultado = det(matrizAux->m, matrizAux->f);        //Calcular el determinante.
                    TextColor(GREEN);
                    printf("\nEl determinante de la matriz es: %.2f\n", resultado);
                    if(matrizAux->liberar == 1){
                        liberarMat(matrizAux);
                        matrizAux = NULL;
                    }
                    break;
                }
                //Comando product(imprimir):
                else if(buscarPalabra(entrada, palabrasABuscar[2]) == 1){

                    token = strtok(entrada, " ");
                    if(strcmp(token, palabrasABuscar[2]) != 0){         //El comando product debe empezar por "product ..."
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando product.\nAsegurese de escribirlo en el formato: 'product n mat' o 'product mat n'.\n");
                        break;
                    }

                    token = strtok(NULL, "");                           //Obtener el resto de la cadena tras product y si es NULO -> ERROR
                    if(token == NULL){
                        TextColor(RED);
                        printf("\nERROR comando product incorrecto.\nFalta informacion en el comando\n");
                        break;
                    }

                    imprimirProdEscalar(token, &lista);                 //Calcular el producto e imprimirlo.
                    break;
                }
                //Comando Save:
                else if(buscarPalabra(entrada, palabrasABuscar[3]) == 1){

                    token = strtok(entrada, "\t ");
                    if(strcmp(token, palabrasABuscar[3]) != 0){         //El comando save debe empezar por "save ..."
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando save.\nAsegurese de escribirlo en el formato: 'save nombrefichero'.\n");
                    }

                    token = strtok(NULL, "\t ");                        //Obtener el resto de la cadena tras save y si es NULO -> ERROR
                    if(token == NULL){
                        TextColor(RED);
                        printf("\nERROR comando save incorrecto.\nFalta informacion en el comando.\n");
                        break;
                    }
                    
                    strcpy(nomArchiu, token);                           //Guardar el nombre del archivo y comprobar que el comando no sigue.

                    token = strtok(NULL, "\t ");
                    if(token != NULL){
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando save.\nSobran argumentos en el comando\n");
                        break;
                    }

                    save(&lista, nomArchiu);                            //Guardar la lista de variables en el fichero.
                    break;
                }
                //Comando Load:
                else if(buscarPalabra(entrada, palabrasABuscar[4]) == 1){

                    token = strtok(entrada, "\t ");
                    if(strcmp(token, palabrasABuscar[4]) != 0){           //El comando load debe empezar por "load ..."
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando load.\nAsegurese de escribirlo en el formato: 'load nombrefichero'.\n");
                        break;
                    }

                    token = strtok(NULL, "\t ");                           //Comprobar que hay tras la palabra load.
                    if(token == NULL){
                        TextColor(RED);
                        printf("\nERROR comando load incorrecto.\nFalta informacion en el comando.\n");
                        break;
                    }

                    else if(strcmp(token, palabrasABuscar[6]) == 0){       //Comprobar si es load o load over.

                        token = strtok(NULL, "\t ");
                        if(token == NULL){
                            TextColor(RED);
                            printf("\nERROR comando load over incorrecto.\nFalta informacion en el comando.\n");
                            break;
                        }

                        strcpy(nomArchiu, token);                           //Guardar el nombre del fichero y comprobar que el comando finaliza.
                        token = strtok(NULL, "\t ");
                        if(token != NULL){
                            TextColor(RED);
                            printf("\nERROR en sintaxis del comando load over.\nSobran argumentos en el comando.\n");
                            break;
                        }

                        loadOver(&lista, nomArchiu);                        //Cargar el fichero añadiendo las variables nuevas a las ya existentes.
                        break;
                    }
                    else{

                        strcpy(nomArchiu, token);                           //Guardar el nombre del fichero y comprobar que el comando finaliza.
                        token = strtok(NULL, "\t ");
                        if(token != NULL){
                            TextColor(RED);
                            printf("\nERROR en sintaxix del comando load.\nSobran argumentos en el comando.\n");
                            break;
                        }

                        load(&lista, nomArchiu);                            //Borrar la lista de variables y cargar el fichero.
                        break;
                    }
                }
                //Comando view:
                else if(buscarPalabra(entrada, palabrasABuscar[5]) == 1){
                    
                    if(strcmp(entrada, palabrasABuscar[5]) != 0){             //El comando view debe ser solo "view"
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando view.\nAsegurese de escribirlo en el formato: 'view'.\n");
                        break;
                    }
                
                    imprimirLista(&lista);
                    break;
                }
                //Comando transp(trasponer matrices):
                else if(buscarPalabra(entrada, palabrasABuscar[7]) == 1){

                    token = strtok(entrada, "\t ");
                    if(strcmp(token, palabrasABuscar[7]) != 0){                 //El comando transp debe empezar por "transp ..."
                        TextColor(RED);
                        printf("\nERROR en el comando transp.\nAsegurese de escribirlo en el formato 'transp mat'.\n");
                        break;
                    }

                    token = strtok(NULL, "");                                   //Obtener la cadena tras "transp" y sacar la matriz.
                    matrizAux = VarOMatliberarMemoria(token, &lista);
                    if(matrizAux == NULL){
                        TextColor(RED);
                        printf("\nERROR: No existe o no se puede crear esa matriz.\n");
                        break;
                    }

                    imprimirTranspuesta(matrizAux);                             //Crear, imprimir y borrar la traspuesta.
                    if(matrizAux->liberar == 1){
                        liberarMat(matrizAux);
                        matrizAux = NULL;
                    }
                    break;
                }
                //Comando help:
                else if(buscarPalabra(entrada, palabrasABuscar[8]) == 1){

                    if(strcmp(entrada, palabrasABuscar[8]) != 0){               //El comando help debe ser "help".
                        TextColor(RED);
                        printf("\nERROR en la sintaxis del comando help.\n");
                        break;
                    }
        
                    help();
                    break;
                }
                //En caso de que el usuario inserte un comando vacío no sé imprime nada.
                else if(strlen(entrada) == 0){
                    continue;
                }

                //Imprimir matrices/variables:
                else{
                    token = strdup(entrada);
                    tokenAux = NULL;
                    
                    //Buscamos si se está realizando alguna operación (+, -, *, $, &)
                    for(int i = 1; i <= 5 && tokenAux == NULL; i++){
                        tokenAux = buscarSigno(token, signosABuscar[i]);
                        if(tokenAux != NULL){
                            operador = signosABuscar[i];
                        }
                    }
                    
                    if(tokenAux != NULL){       //En caso de que haya una operación la cadena se divide en 2.
                        Trim(token);
                        Trim(tokenAux);

                        matrizAux = VarOMatliberarMemoria(token, &lista);       //Obtener la primera matriz.

                        if(matrizAux == NULL){
                            free(token);
                            token = NULL;
                            tokenAux = NULL;
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear la matriz 1.\n");
                            break;
                        }

                        matrizAux2 = VarOMatliberarMemoria(tokenAux, &lista);   //Obtener la seguna matriz.

                        if(matrizAux2 == NULL){
                            if(matrizAux->liberar == 1){
                                liberarMat(matrizAux);
                                matrizAux = NULL;
                            }
                            free(token);
                            token = NULL;
                            tokenAux = NULL;
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear la matriz 2.\n");
                            break;
                        }

                        //Según el operador realizamos alguna de las operaciones.
                        if(strcmp(operador, signosABuscar[1]) == 0){
                            imprimirSumaMatrices(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, signosABuscar[2]) == 0){
                            imprimirRestaMatrices(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, signosABuscar[3]) == 0){
                            imprimirMultiplicacionMatrices(matrizAux,matrizAux2);
                        }
                        else if(strcmp(operador, signosABuscar[4]) == 0){
                            productoEscalar2Mat(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, signosABuscar[5]) == 0){
                            imprimirConcatenacion(matrizAux, matrizAux2);
                        }

                        //Liberar memoria.
                        free(token);
                        free(tokenAux);
                        token = NULL;
                        if(matrizAux->liberar == 1){
                            liberarMat(matrizAux);
                            matrizAux = NULL;
                        }
                        if(matrizAux2->liberar == 1){
                            liberarMat(matrizAux2);
                            matrizAux2 = NULL;
                        }
                        break;
                    }
                    else{                       //En caso de que no hay operación trabajamos directamente con dicha cadena.
                        Trim(token);

                        matrizAux = VarOMatliberarMemoria(token, &lista);       //Obtener la matriz.
                        
                        if(matrizAux == NULL){
                            free(token);
                            token = NULL;
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear esa matriz.\n");
                            break;  
                        }

                        imprimirMatriz(matrizAux);

                        //Liberar memoria.
                        free(token);
                        token = NULL;
                        if(matrizAux->liberar == 1){
                            liberarMat(matrizAux);
                            matrizAux = NULL;
                        }
                        break;
                    }
                }
                break;
           
            case 1:              //Asignar matrices:
                //Asignar usando comando Product:
                if (buscarPalabraClave(entrada, palabrasABuscar[2]) == 1){

                    token = strdup(entrada);
                    tokenAux = buscarSigno(token, signosABuscar[0]);        //Separar la cadena en 2, antes y después del igual.
                    if(strcmp(token, "\0") == 0){                           //Si la parte del nombre de la Variable no está -> ERROR
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando product.\nFalta el nombre de la variable que se va crear.\n");
                        break;
                    }

                    Trim(token);
                    Trim(tokenAux);

                    if(comprobarNombre(token, palabrasABuscar, nPalabras) == 0){    //Comprobar que sea un nombre válido.
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }

                    nuevaVariable = buscarVariable(&lista, token);                  //Comprobar si la Variable ya existe.
                    
                    if(nuevaVariable == NULL){                                      //En caso de ser nueva nos quedamos con su nombre.
                        strcpy(nomVar1, token); 
                    }
                                                            
                    token = strtok(tokenAux, "\t ");                                //Ver si lo primero tras el '=' es product
                    if(strcmp(token, palabrasABuscar[2]) != 0){
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando product.\nAsegurese de escribirlo en el formato: 'var = product n mat' o 'var = product mat n'.\n");
                        break;
                    }

                    token = strtok(NULL, "");                                      //Ver si hay algo detrás del product
                    if(token == NULL){
                        free(token);
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en el comando product.\nFalta informacion en el comando.\n");
                        break;
                    }

                    nuevaMatriz = VarOMatPE(token, &lista);                        //Obtener el producto de la matriz por el escalar.
                    if(nuevaMatriz == NULL){
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }

                    if(nuevaVariable == NULL){                                      //Si es nueva la variable se crea
                        nuevaVariable = crearVariableExplicita(nuevaMatriz, nomVar1);
                        agregarVarAlFinalDeLista(&lista, nuevaVariable);
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }
                
                    liberarMat(nuevaVariable->mat);                                 //En caso de no ser nueva la actualizamos.
                    nuevaVariable->mat = nuevaMatriz;
                    free(token);
                    token = NULL;
                    tokenAux = NULL;
                    break;
                }
                //Asignar usando comando Transp:
                else if(buscarPalabraClave(entrada, palabrasABuscar[7]) == 1){

                    token = strdup(entrada);
                    tokenAux = buscarSigno(token, signosABuscar[0]);                //Separar la cadena en 2, antes y después del igual.
                    if(strcmp(token, "\0") == 0){
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando product.\nFalta el nombre de la variable que se va crear.\n");
                        break;
                    }

                    Trim(token);
                    Trim(tokenAux);
                    if(comprobarNombre(token, palabrasABuscar, nPalabras) == 0){   //Comprobar nombre variable
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }

                    nuevaVariable = buscarVariable(&lista, token);                  //Comprobar que ya existe:

                    if(nuevaVariable == NULL){
                        strcpy(nomVar1, token);
                    }
                    
                    token = strtok(tokenAux, " ");
                    if(strcmp(token, palabrasABuscar[7]) != 0){                     //Ver si lo primero tras el '=' es transp:
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en sintaxis del comando transp.\nAsegurese de escribirlo en el formato: 'var = transp mat'.\n");
                        break;
                    }

                    token = strtok(NULL, "");                                       //Comprobar que tras el trasnp hay algo:               
                    if(token == NULL){
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR en el comando transp.\nFalta informacion en el comando.\n");
                        break;
                    }

                    matrizAux = VarOMatliberarMemoria(token, &lista);                  //Obtener la matriz
                    if(matrizAux == NULL){
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("ERROR en sintaxis del comando transp.\nNo ha sido posible leer la matriz.");
                        break;
                    }

                    nuevaMatriz = traspuesta(matrizAux);                                //Obtener la traspuesta de esa martriz

                    if(nuevaVariable == NULL){                                          //En caso de ser variable nueva se crea.
                        nuevaVariable = crearVariableExplicita(nuevaMatriz, nomVar1);
                        agregarVarAlFinalDeLista(&lista, nuevaVariable);
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }

                    liberarMat(nuevaVariable->mat);                                        //En caso de que ya exista se actualiza.
                    nuevaVariable->mat = nuevaMatriz;
                    free(token);
                    token = NULL;
                    tokenAux = NULL;
                    break;
                }

                //Asignar a una variable una matriz directamente(sin usar comandos):
                else{
                    //strdup = Hace el malloc y copy en un momento -> token = strdup(entrada).
                    token = strdup(entrada);
                    tokenAux = buscarSigno(token, signosABuscar[0]);    //Separar la cadena en 2, antes y después del '='
                    
                    //Así evitamos que el usuario escriba 'var ='.
                    if(tokenAux == NULL || token == NULL){              //Las 2 partes deben contener algo
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        TextColor(RED);
                        printf("\nERROR: Falta informacion en el comando.\n");
                        break;
                    }

                    Trim(token);
                    Trim(tokenAux);

                    if(comprobarNombre(token, palabrasABuscar, nPalabras) == 0){    //Comprobar su nombre
                        free(token);
                        token = NULL;
                        tokenAux = NULL;
                        break;
                    }

                    nuevaVariable = buscarVariable(&lista, token);                  //Comprobar si ya existe

                    if(nuevaVariable == NULL){
                        strcpy(nomVar1, token);
                    } 
                    
                    token = NULL;
                    operador = NULL;

                    for(int i = 1; (i <= 5 && token == NULL); i++){                 //Averiguar si se realiza alguna operación.
                        token = buscarSigno(tokenAux, signosABuscar[i]);
                        if(token != NULL){
                            operador = signosABuscar[i];
                        }
                    }                  

                    if(token != NULL){                                              //Si se realizan operaciones debemos obtener las 2 matrices  de token y tokenAux

                        Trim(token);
                        Trim(tokenAux);
                        
                        matrizAux = VarOMatliberarMemoria(tokenAux, &lista);        //Obtener primera matriz

                        if(matrizAux == NULL){
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear la matriz 1.\n");
                            break;
                        }

                        matrizAux2 = VarOMatliberarMemoria(token, &lista);          //Obtener segunda matriz

                        if(matrizAux2 == NULL){
                            if(matrizAux->liberar == 1){
                                liberarMat(matrizAux);
                                matrizAux = NULL;
                            }
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear la matriz 2.\n");
                            break;
                        }

                        //Liberar cadenas:
                        free(token);
                        free(tokenAux);
                        token = NULL;
                        tokenAux = NULL;

                        //Según el operador realizar la operación correspondiente.
                        if(strcmp(operador, "+") == 0){
                            nuevaMatriz = crearMatrizSuma(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, "-") == 0){
                            nuevaMatriz = crearMatrizResta(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, "*") == 0){
                            nuevaMatriz = crearMatrizMultiplicacion(matrizAux, matrizAux2);
                        }
                        else if(strcmp(operador, "&") == 0){
                            nuevaMatriz = concatenarMatrizFilas(matrizAux, matrizAux2);
                        }

                        if(matrizAux->liberar == 1){
                            liberarMat(matrizAux);
                            matrizAux = NULL;
                        }
                        if(matrizAux2->liberar == 1){
                            liberarMat(matrizAux2);
                            matrizAux2 = NULL;
                        } 
                    }
                    else{

                        Trim(tokenAux);
                        matrizAux = VarOMatliberarMemoria(tokenAux, &lista);            //Obtener directamente la matriz.

                        if(matrizAux == NULL){
                            TextColor(RED);
                            printf("\nERROR: No existe o no se puede crear esa matriz.\n");
                            break;
                        }
                        nuevaMatriz = copiarMatriz(matrizAux);
                        if(matrizAux->liberar == 1){
                            liberarMat(matrizAux);
                            matrizAux = NULL;
                        }
                    }
                            
                    if(nuevaMatriz != NULL){                                        
                        if(nuevaVariable == NULL){                                          //Si es una Variable nueva se crea
                            nuevaVariable = crearVariableExplicita(nuevaMatriz, nomVar1);
                            agregarVarAlFinalDeLista(&lista, nuevaVariable);
                            break;
                        }
                        else{   
                            liberarMat(nuevaVariable->mat);                                 //Si no se actualiza.
                            nuevaVariable->mat = nuevaMatriz;
                            break;
                        }
                    }
                }   
        }
    }
    // Liberar Memoria y cerrar el programa:
    liberarLista(&lista);
    printf("\nexit ok\n");

}