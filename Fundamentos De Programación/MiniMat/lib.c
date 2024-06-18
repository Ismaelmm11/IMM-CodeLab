#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>
#include <windows.h>

#include "lib.h"

/*
----------------------------------EN ESTE ARCHIVO SE ENCUENTRAN TODAS LAS FUNCIONES QUE USA EL PROGRAMA----------------------------------------------------------------
*/

/**
 * Método para eliminar el salto de línea al final de la cadena si existe. 
 * Esto sirve para evitar el problema que genera fgets, ya que fgets lee hasta que llega al nº de caracteres que limita la función(sizeof(...)) 
 * o hasta que encuentra un salto de línea. Si encuentra el salto antes de llegar al límite, lo deja dentro de la cadena y eso puede dar problemas
 * a la hora de trabajar con cadenas.
 * @param Entrada Cadena introducida por el usuario.
*/
void eliminarSaltoLinea(char *entrada){
    if (entrada[strlen(entrada) - 1] == '\n') {
           entrada[strlen(entrada) - 1] = '\0';     //Intercambiar '\n' por '\0'.
    }
}

/**
 * Método que elimina espacios, saltos de línea, retornos de carro y tabulaciones al inicio y al final de una cadena.
 * @param Entrada Cadena a modificar.
*/
void Trim(char *entrada)
{
    int i, j, x;
    i = 0;                      //Indice donde empieza realmente la cadena.
    j = strlen(entrada) - 1;    //Indice donde acaba realmente la cadena
    x = 0;
    

    //Buscamos alguno de estos caractéres antes de la cadena.
    while(entrada[i] == ' ' || entrada[i] == '\n' || entrada[i] == '\t' || entrada[i] == '\r')
    {
        i++;
    }

    //Buscamos detrás de la cadena.
    while(entrada[j] == ' ' || entrada[j] == '\n' || entrada[j] == '\t' || entrada[j] == '\r')
    {
        j--;
    }

    //Creamos la cadena desde cero sin incluir lo hay antes del inicio y después del final.
    while(i <= j){
        entrada[x] = entrada[i];
        i++;
        x++;
    }

    //Finalizamos cerrando la cadena.
    entrada[x] = '\0';
}

/**
 * Errores:
 * 0:   Error en el comando.
 * 1:   Variable no encontrada.
 * 2:   Escalar no válido.
 * 3:   Nombre de variable.
 * 4:   Error dimensiones matriz.
*/
void imprimirError(int eleccion){
    TextColor(RED);
    if(eleccion == 0){
        printf("\nERROR: Comando incorrecto, asegurese de escribirlo bien.\n");
    }
    else if(eleccion == 1){
        printf("\nERROR: La variable no existe dentro de la lista,\npor favor creala antes de usar este comando.\n");
    }
    else if(eleccion == 2){
        printf("\nERROR: El escalar no es un numero valido.\n");
    }
    else if(eleccion == 3){
        printf("\nERROR: El nombre de la variable es superior a 15 caracteres.\n");
    }
    else if(eleccion == 4){
        printf("\nERROR: Las matrices con las que se opera\ndeben tener las mismas dimensiones (filas y columnas)");
    }
}


/**
 * Método que verifica la existen una cadena al principio de otra cadena.
 * @param Entrada Cadena en la que se busca.
 * @param Palabra Cadena que se busca.
 * @return 1 si la palabra está presente, en caso contrario 0.
*/
int buscarPalabra(const char *entrada, const char *palabra)
{
    int longitud = strlen(palabra);

    char *p = strstr(entrada, palabra);
    // Verificar que no haya nada antes de la cadena
    if (p == entrada) {
        // Verificar que no haya nada o que haya un espacio después de la cadena
        char siguienteCaracter = p[longitud];
        if (siguienteCaracter == '\0' || isspace(siguienteCaracter)) {
            return 1;
        }
    }
    return 0;
}


/**
 * Método que nos sirve para saber si una cadena clave se encuentra dentro de otra cadena.
 * @param entrada Cadena en la que se busca.
 * @param palabra Cadena que se busca.
 * @return 1 si la palabraa está presente, en caso contrario 0.
 */
int buscarPalabraClave(const char *entrada, const char *palabra)
{
    int longitud = strlen(palabra);

    char *p = strstr(entrada, palabra);
    if(p != NULL){
        return 1;
    }
    return 0;
}

/**
 * Método para eliminar los 2 paréntesis que tiene una cadena al principio y al final.
 * @param Entrada Es la cadena a modificar
*/
void eliminarParentesis(char *entrada)
{
    int longitud = strlen(entrada);

    for(int i = 0; i < longitud - 1; i++){
        entrada[i] = entrada[i + 1];        //Desplazamos cada carácter a la izquierda.
    }

    //Sustituimos el paréntesis del final ")" con un "\0".
    entrada[longitud - 2] = '\0';
}

/**
 * Método que verifica que una cadena es un número válido.
 * @param Token Cadena que se desea verificar.
 * @return 1 si cadena es un número válido o 0 si no lo es.
*/
int es_numero(const char *token)
{
    int i;
    int punto = 0;

    //¿La cadena es nula o vacía?
    if(token == NULL || token[0] == '\0'){
        return 0;
    }
    
    //Permitimos que pasen números con "+" o "-".
    if(token[0]=='+' || token[0]=='-'){
        //Evitamos que pase solo el símbolo sin ningun numero detrás.
        if(strlen(token) == 1){
            return 0;
        }
        i=1;//Así comenzamos la verificación desde el segundo carácter.
    }
    else{
        i=0;
    }

    while(token[i]!='\0'){
        if(!isdigit(token[i])){
            //Así permitimos que solo pueda haber un "." .
            if(token[i] == '.' && punto == 0){
                punto++;
            }
            else{
                return 0;
            }
        }
        i++;
    }
    return 1;
}

/**
 * Método que cuenta las columnas que hay en una fila de una matriz explícita y asegura que solo contiene números.
 * @param Token Es una cadena que incluye los números de una fila.
 * @return El nº de columnas de una fila.
*/
int contarColumnas(char *token){
    
    char *tokenAux;
    int contador = 0;
    char *delimitador = "\t ";
    
    //Dividimos el token según sus "\t" o " ".
    while((tokenAux = strtok_r(token, delimitador, &token) )){
        contador++;
        if(es_numero(tokenAux) == 0){
            return 0;
        }
    }
    return contador;
}

/**
 * Método que se usa para verificar que todas las filas de la matriz tengan el mismo número de columnas y solo tengan números.
 * @param Entrada Es una cadena que contiene la matriz introducida por el usuario.
 * @return Columnas que es un entero con el nº de columnas de la matriz o 0 si hay filas con diferente nº de columnas.
*/
int verificarColumnas(char *entrada)
{
    char *delimitador = "|";
    int columnas = 0;
    int columnasAux = 0;

    //Obtenemos la primera fila y contamos sus columnas.
    char *token = strtok(entrada, delimitador);  
    columnas = contarColumnas(token);

    if(columnas == 0){
        return 0;
    }
    
    token = strtok(NULL, delimitador);
    //Repetimos el proceso con todas las filas.
    while (token != NULL) {               
        columnasAux = contarColumnas(token);
        //Aseguramos que todas las filas tengan el mismo nº de columnas.
        if(columnas != columnasAux){
            return 0;
        }
        token = strtok(NULL, delimitador);
    }

    return columnas;
}

/**
 * Método para contar las filas de una matriz explícita usando el carácter "|".
 * @param Entrada es una cadena que contiene la matriz introducida por el usuario.
 * @return Filas es un entero que contiene el nº de filas de la matriz.
*/

int contarFilas(char *entrada)
{
    //El número de filas es 1 más la cantidad de '|' que tenga la entrada.
    int filas = 1;

    for(int i = 0; i < strlen(entrada); i++){
        if(entrada[i] == '|'){
            filas++;
        }
    }
    return filas;
}

/**
 * Método que verifica si una cadena está vacía o no (al menos que contenga un espacio).
 * @param Entrada Es la cadena a verificar.
 * @return 1 si no está vacía, en caso contrario 0.
*/
int es_Vacia(char *entrada)
{
    if(strlen(entrada) == 0){
        return 0;
    }

    int contador = 0;
    for(int i = 0; i < strlen(entrada); i++){
        if(entrada[i] == ' ' || es_numero(&entrada[i]) == 1){
            contador++;
        }
    }

    if(contador == 0){
        return 0;
    }
    return 1;
}

/**
 * Método que comprueba que la cadena es una matriz válida. Elimina sus paréntesis, verifica si está vacía o contiene algo.
 * Además cuenta las filas y columnas de la matriz y se asegura que todas las filas tienen el mismo nº de columnas.
 * @param Entrada Es una cadena que representa la matriz.
 * @param Filas Es un puntero para almacenar el nº de filas.
 * @param Columnas Es un puntero para almacenar el nº de columnas.
 * @return 1 si es una matriz válida, en otro caso 0.
*/
int validarMatriz(char *entrada, int *filas, int *columnas)
{
    int longitud = strlen(entrada);

    //Verificar que la cadena no solo es
    if(longitud > 0){
        //Verificar si la matriz empieza con "(" y acaba con ")".
        if(entrada[0] == '(' && entrada[longitud-1] == ')'){
            eliminarParentesis(entrada);
        }
        else {
            return 0;
        }

        int clave = es_Vacia(entrada);
        if (clave == 0){
            return 0;
        }
        
        *filas = contarFilas(entrada);
        *columnas = verificarColumnas(entrada);
        if(*columnas == 0){
            return 0;
        }
        return 1;  
    }
    return 0;
}

/**
 * Método que sirve para crear una matriz bidimensional de tipo double.
 * @param Filas Es el nº de filas de la matriz.
 * @param Columnas Es el nº de columnas de la matriz.
 * @return Puntero a la nueva matriz si la creación ha sido exitosa, pero si falla la asignación de memoria devuelve NULL.
*/
double **crearMatriz(int filas, int columnas)
{
    double **m;

    //Asignar memoria dinámicamente para las filas de la matriz.
    //Aquí m es un array de punteros o un puntero a punteros(double).
    m = (double**)malloc(filas * sizeof(double*));
    if(m == NULL){
        return NULL;
    }
    //Asignar dinámicamente memoria para cada fila de la matriz.
    //Donde m[i] es un puntero a una fila, básicamente un array de double.
    for(int i = 0; i < filas; i++){
        m[i] = (double*)malloc(columnas * sizeof(double));

        if(m[i] == NULL){
            liberarMatriz(m, filas);
            return NULL;
        }
    }

    return m;
}

/**
 * Método para crear una estructura TMatriz, que contendrá un matriz double bidimensional.
 * @param Filas Son las filas de la TMatriz.
 * @param Columas Son las columnas de la TMatriz.
 * @return Devuelve un puntero a la nueva estructura TMatriz si todo va bien, sino devolverá NULL.
*/
TMatriz *crearTMatriz(int filas, int columnas)
{
    //Asignar memoria dinámicamente para la nueva TMatriz.
    TMatriz *nuevaMatriz = (TMatriz*)malloc(sizeof(TMatriz));
    if(nuevaMatriz == NULL){
        return NULL;
    }

    //Asignar el nº de columnas y filas.
    nuevaMatriz->f = filas;
    nuevaMatriz->c = columnas;

    //Crear la nueva matriz bidimensional.
    nuevaMatriz->m = crearMatriz(nuevaMatriz->f,nuevaMatriz->c);
    if(nuevaMatriz->m == NULL){
        free(nuevaMatriz);
        nuevaMatriz = NULL;
        return NULL;
    }

    return nuevaMatriz;
}


/**
 * Método que crea una TMatriz introducida explícitamente por el usuario en una cadena.
 * @param entrada es una cadena que contiene la matriz explícita.
 * @return NuevaMatriz que es un puntero del tipo TMatriz si ha sido creada correctamente o NULL si la matriz no ha podido ser creada.
*/
TMatriz *crearMatrizExplicita(char *entrada)
{
    //Asignar memoria dinámicamente para un nuevo objeto de TMatriz.
    //*nuevaMatriz es un puntero de tipo TMatriz y apuntará al bloque de memoria recién asignado del tamaño de TMatriz.
    TMatriz *nuevaMatriz = (TMatriz*)malloc(sizeof(TMatriz));
    if(nuevaMatriz == NULL){
        return NULL;
    }

    //Duplicamos la cadena.
    char *entradaAux = strdup(entrada);

    //Validar la matriz y obtener sus dimensiones.
    if (validarMatriz(entradaAux, &nuevaMatriz->f, &nuevaMatriz->c) == 0){
        free(entradaAux);
        free(nuevaMatriz);
        entradaAux = NULL;
        nuevaMatriz = NULL;
        return NULL;
    }

    free(entradaAux);
    entradaAux = NULL;

    //Crear la matriz bidimensional con las dimensiones obtenidas.
    nuevaMatriz->m = crearMatriz(nuevaMatriz->f, nuevaMatriz->c);
    if(nuevaMatriz->m == NULL){
        free(nuevaMatriz);
        nuevaMatriz = NULL;
        return NULL;
    }

    //Quitar paréntesis para que no afecten a la lectura de la matriz.
    eliminarParentesis(entrada);

    //Troceamos la cadena en tokens según "|", usando strtok y así obtenemos los números de cada fila.
    int contador = 0;
    char *delimitador = "|";
    char *token = strtok(entrada, delimitador);
    
    while(token != NULL)
    {
        //Procedemos a leer las columnas(números) de cada token(fila) y asignarlos a la matriz bidimensional.
        leerMatrizExplicita(nuevaMatriz->m, token, contador);
        token = strtok(NULL, delimitador); //Recuerda: Le pasamos NULL, para indicar que siga troceando donde lo dejó en la última llamada.
        contador++;
    }

    return nuevaMatriz;
}

/**
 * Método que se encarga de leer convertir los números que hay en un cadena en double, 
 * los cuales se añadirán a la matriz bidimensional.
 * @param m Es un puntero a la matriz bidimensional donde se guardarán los números.
 * @param token Es un cadena que representa las filas de la matriz.
 * @param contador Es el índice que indica la fila de la matriz.
*/
void leerMatrizExplicita(double **m, char *token, int contador){
    char *tokenAux;
    char * delimitador = "\t ";
    int j = 0;
    
    //Dividimos los token según si hay "\t "; el bucle sigue hasta que tokenAuX sea igual a NULL.
    while((tokenAux = strtok_r(token, delimitador, &token))){
        //Parseamos el tokenAux para convertirlo en un double.
        double num = atof(tokenAux);
        //Introducir el valor double a la matriz.
        m[contador][j] = num;
        j++;
    }
}

/** 
 * Método que crea una variable con la TMatriz y nombre proporcionados.
 * @param Matriz Es un puntero a una estructura TMatriz, que se insertará en TVar.
 * @param Nombre es una cadena que contiene el nombre de la variable a crear.
 * @return nUn puntero a la nueva variable recién creada o NULL si falla la asignación.
*/
TVar *crearVariableExplicita(TMatriz *matriz, const char nombre[50])
{   
    //Reservar memoria de tamaño TVar dinámicamente para un nuevo objeto de TVar.
    //Donde nuevaVariable será un puntero de TVar.
    if(matriz != NULL){
        TVar *nuevaVariable = (TVar*)malloc(sizeof(TVar));
        if(nuevaVariable == NULL){
            TextColor(RED);
            printf("\nERROR: No se ha podido reservar la memoria para la variable.\n");
            return NULL;
        }

        //Copiamos el nombre al char nomVar de nuevaVariable.
        strcpy(nuevaVariable->nomVar, nombre);

        //Inicializamos los valores de la variable
        nuevaVariable->mat = matriz;
        nuevaVariable->sig = NULL;
        nuevaVariable->ant = NULL;

        return nuevaVariable;
    }

    TextColor(RED);
    printf("\nERROR: No se puede crear una variable con una matriz que no existe.\n");
    return NULL;
}

/**
 * Método para agregar una Variable al final de la lista doblemente enlazada. Si la lista está vacía la variable se
 * asignará como primera de la lista.
 * @param Lista es un puntero a una estructura TVars, que representa la lista de variables.
 * @param Variable es un puntero a una estructura TVar, que es la variable a insertar a la lista.
*/
void agregarVarAlFinalDeLista(TVars *lista, TVar *variable)
{
    TVar *actual;

    //Verificamos si existe la variable.
    if(variable == NULL){
        liberarVariable(variable);
        TextColor(RED);
        printf("\nERROR: La variable que se desea agregar a la lista no existe.\n");
        return;
    }

    //Si la lista está vacía asignamos la variable como primera.
    if(lista->primera == NULL){
        lista->primera = variable;
    }

    //En caso contrario, la metemos al final.
    else{
        actual = lista->primera;
        //Bucle para recorrer la lista y llegar al final.
        while(actual->sig != NULL){
            actual = actual->sig;
        }
        //Colocamos la variable al final, ajustando los punteros.
        actual->sig = variable;
        variable->ant = actual;
    }
    //Incrementamos el nº de variables de la lista.
    lista->numVars++;
}

/**
 * Método para imprimir el contenido de una matriz bidimensional que hay dentro de una TMatriz.
 * @param Matriz es un puntero a una estructura TMatriz y contiene la matriz en su interior.
*/
void imprimirMatriz(TMatriz *matriz)
{
    TextColor(GREEN);
    //Si la estructura matriz existe se imprimirá.
    if(matriz != NULL){
        for(int i = 0; i < matriz->f; i++){
            for(int j = 0; j < matriz->c; j++){
                printf("%.2f\t", matriz->m[i][j]);
            }
            printf("\n");
        }
    }
}

/**
 * Método que imprime por consola la información de una variable, como lo es
 * su nombre, dimensiones y su primera fila.
 * @param Variable Es un puntero al TVar que se desea imprimir.
*/
void imprimirVariable(TVar *variable)
{
    TextColor(GREEN);
    //Confirmamos que la variable existe.
    if(variable != NULL){
        TMatriz *matrizAux = variable->mat;
        printf("%s (%i x %i) : ( ", variable->nomVar, matrizAux->f, matrizAux->c);
        for(int i = 0; i < matrizAux->c; i++){
            printf("%.2f ", matrizAux->m[0][i]);
        }
        printf(")\n");
        /*
        Para imprimir la variable completa
        for(int i = 0; i < matrizAux->f; i++){
            for(int j = 0; j < matrizAux->c; j++){
                printf("%.2f ", matrizAux->m[i][j]);
            }
            if(i == (matrizAux->f - 1)){
                printf(")\n");
            }
            else{
                printf("| ");
            }
        }
        */
    }
}

/**
 * Método para imprimir la información de todas las variables de la lista.
 * @param Lista es un puntero a TVars y contiene todas lass variables creadas.
*/
void imprimirLista(TVars *lista)
{
    //Asignamos la primera variable a actual y procedemos a recorrer la lista e imprimir.

    ordenarLista(lista);

    TVar *actual = lista->primera;
    if(actual == NULL){
        TextColor(RED);
        printf("\nLa lista esta vacia.\n");
        return;
    }
    while(actual != NULL)
    {   
        imprimirVariable(actual);
        actual = actual->sig;
    }
    TextColor(GREEN);
    printf("\nLa lista contiene %i variables.\n", lista->numVars);
}

void intercambiar(TVar* variable1, TVar* variable2) 
{
    char aux[16];
    TMatriz* matrizAux = copiarMatriz(variable1->mat);
    if(matrizAux == NULL){
        return;
    }

    strcpy(aux, variable1->nomVar);
    liberarMat(variable1->mat);
    variable1->mat = copiarMatriz(variable2->mat);

    strcpy(variable1->nomVar, variable2->nomVar);
    liberarMat(variable2->mat);
    variable2->mat = matrizAux;
    strcpy(variable2->nomVar, aux);
}

// Función para ordenar alfabéticamente la lista doblemente enlazada
void ordenarLista(TVars* lista) 
{
    int intercambiado;
    TVar* ptr1;
    TVar* lptr = NULL;

    // Verificar si la lista está vacía o tiene solo un elemento
    if (lista->primera == NULL || lista->primera->sig == NULL) {
        return;
    }

    do {
        intercambiado = 0;
        ptr1 = lista->primera;

        while (ptr1->sig != lptr) {
            if (strcmp(ptr1->nomVar, ptr1->sig->nomVar) > 0) {
                intercambiar(ptr1, ptr1->sig);
                intercambiado = 1;
            }
            ptr1 = ptr1->sig;
        }
        lptr = ptr1;
    } while (intercambiado);
}


//Funciones para liberar memoria(verificamos que todos los punteros no son NULL para evitar errores):

/**
 * Método para liberar la memoria de una matriz bidimensional.
 * @param Matriz Es un puntero a la matriz bidimensional.
 * @param Filas Es el nº de filas de la matriz.
*/
void liberarMatriz(double **matriz, int filas)
{
    if(matriz != NULL){
        for(int i = 0; i < filas; i++)
        {  
            if(matriz[i] != NULL){
                free(matriz[i]);
            }
            else
            {
                break;
            }
        }
        free(matriz);
    }

    matriz = NULL;
}

/**
 * Método que libera la memoria de una TMatriz.
 * @param Matriz Puntero a una estructura TMatriz.
*/
void liberarMat(TMatriz *matriz)
{
    if(matriz != NULL){
        liberarMatriz(matriz->m, matriz->f);
        free(matriz);
        matriz = NULL;
    }
}

/**
 * Método que libera la memoria de una variable(TVar).
 * @param Variable Puntero a una estructura TVar.
*/
void liberarVariable(TVar *variable)
{
    if(variable != NULL){
        liberarMat(variable->mat);
        free(variable);
        variable = NULL;
    }
}

/**
 * Método para liberar la memoria de una variable y ajustar los punteros de la lista.
 * @param Variable Es un puntero a TVar.
*/
void liberarUnaVariable(TVars *lista, char *nombre)
{
    if(lista->primera == NULL){
        TextColor(RED);
        printf("\nERROR: La lista esta vacia.\n");
        return;
    }

    TVar *variable = buscarVariable(lista, nombre);
    if(variable != NULL){
        if(variable == lista->primera){
            lista->primera = variable->sig;
            if(variable->sig != NULL){
                variable->sig->ant = NULL;
            }
            liberarVariable(variable);
            return;
        }
        else{
            if(variable->ant != NULL){
                variable->ant->sig = variable->sig;
            }
            if(variable->sig != NULL){
                variable->sig->ant = variable->ant;
            }
            
        }
        liberarVariable(variable);   
        return;
    }
    TextColor(RED);
    printf("\nNo se ha podido borrar la variable %s porque no existe en la lista.\n", variable->nomVar);
}

/**
 * Libera la memoria asignada a la lista de variables y reestablece los punteros.
 * @param Lista Es un puntero a TVars, es decir a la lista.
*/
void liberarLista(TVars *lista)
{
    TVar *actual = lista->primera;
    TVar *actualAux;
    while(actual != NULL)
    {
        actualAux = actual;
        actual = actual->sig;
        liberarVariable(actualAux);
    }

    lista->primera = NULL;
    lista->numVars = 0; 
}

/**
 * Método que busca un signo válido(sin número detrás) en la cadena, de manera que si lo encuentra divide la cadena en 2.
 * En la cadena original solo se quedará la parte de la cadena que hay a la izquierda del signo.
 * @param Entrada Cadena en la que se busca el signo.
 * @param Signo Signo a buscar.
 * @return Parte de la cadena que esta a la derecha del signo en la cadena original. 
 * Si no encuentra el signo devuelve NULL y no se divide la cadena.
*/
char *buscarSigno(char *entrada, char *signo)
{
    char *p = entrada;

    do{
        //Buscar el signo en la cadena, empezando desde la posición 0.
        //Cada vez que aumenta p se va avanzando la posicion en la que busca.
        p=strstr(p,signo);
        if(p == NULL){
            return NULL;
        }

        if(p[1] == '\0'){
            return NULL;
        }

        //Si detrás del signo hay un punto lo ignoramos y seguimos.
        if(p[1] == '.'){
            p++;
            continue;
        }

        //El signo que buscamos no ha de tener ningún número detrás.
        //Excepto si el signo es "=", entonces da igual.
        if((p[1] < '0' || p[1] > '9') || strcmp(signo, "=") == 0){
            *p = '\0';
            return p + strlen(signo); 
        }

        //Avanzamos para seguir con la búsqueda.
        p++;
    }
    while(p[0] != '\0');
    return NULL;
}


/**
 * Localiza y extrae una matriz de una cadena dada.
 * @param Entrada Es la cadena en la que buscamos la matriz.
 * @return Una cadena que contiene la matriz si la encuentra, sino devolverá NULL.
*/
char *localizarMatriz(char *entrada) 
{
    char *p = entrada;
    char *p2 = NULL;

    //Buscamos el "(".
    p = strstr(p, "(");
    if (p == NULL) {
        return NULL;
    }

    //Buscamos el ")", después del primer "(".
    p2 = strstr(p, ")");
    if (p2 == NULL) {
        return NULL;
    }

    //Si antes del "(" hay un espacio, entonces el ")" debe ser el último carácter de la cadena.
    if(p[-1] == ' '){
        if(p2[1] != '\0'){
            return NULL;
        }
    }

    //Si después del ")" hay un espacio, entonces el "(" debe ser el primer carácter de la cadena.
    else  if(p2[1] == ' '){
        if(*p != entrada[0]){
            return NULL;
        }
    }
    //Con este if no se permite que se haga un "2(1 2 3)". 
    else if(p[-1] != '\0' && p2[1] == '\0'){
        return NULL;
    }

    //Longitud de la matriz.
    int longitud = p2 - p + 1;

    //Asif¡gnamos memoria para poder copiar la matriz.
    char *contenidoMatriz = malloc(longitud); 
    if (contenidoMatriz == NULL) {
        return NULL;
    }

    strncpy(contenidoMatriz, p, longitud);
    contenidoMatriz[longitud] = '\0'; 

    //Con esto verificamos que si después del ")" no hay un "\0" debe haber un espacio obligatoriamente.
    if (p2[1] != '\0' && p2[1] != ' ') {
        free(contenidoMatriz); 
        contenidoMatriz = NULL;
        return NULL;
    }
    strcpy(p, p2 + 1);

    return contenidoMatriz;
}

/**
 * Comprueba la cantidad de signos iguales que hay en la cadena.
 * @param Entrada Es la cadena que hay que comprobar.
 * @return 0 si no hay "=", 1 si hay solo 1 "=" o 2 si hay más de 1 "=".
*/
int comprobarIguales(char *entrada)
{
    //Buscar el primer signo igual.
    char *p1 = strstr(entrada, "=");
    if(p1 == NULL){
        return 0;//No hay "=" en la cadena.
    }
    else{
        //Si se ha encontrado 1 buscar otro.
        char *p2 = strstr(p1+1, "=");
        //Verificar si hay otro.
        if(p2 == NULL){
            return 1;//Solo hay 1 "=".
        }
        else{
            TextColor(RED);
            printf("\nERROR: Comando incorrecto.\nEn la declaracion solamente se puede usar un '='.\n");
            return 2;//Hay más de 1 "=".
        }
    }
}

/**
 * Comprueba si el nombre de la variable es válido.
 * @param Entrada Es la cadena con el nombre a comprobar.
 * @param PalabrasABuscar Array que contiene los comandos del programa.
 * @param LongitudArr Es un entero que define la longitud del Array de comandos.
 * @return 1 si el nombre es válido, sino se devolverá 0.
*/
int comprobarNombre(char *entrada, char *palabrasABuscar[], int longitudArr)
{
    int longitud = strlen(entrada);

    //Comprobar que no supera los 15 caracteres.
    if(longitud > 15){
        TextColor(RED);
        printf("\nERROR: El nombre supera la longitud maxima de 15 caracteres.\n");
        return 0;
    }

    //Comprobar que el primer carácter sea alfabético, es decir, una letra.
    if(!isalpha(entrada[0])){
        TextColor(RED);
        printf("\nERROR: El nombre de las variables tiene que empezar por un caracter.\n");
        return 0;
    }

    //Comprobar que el resto de caracteres son alfanúmericos.
    for(int i = 1; i <= (strlen(entrada)-1); i++){
        if(!isalnum(entrada[i])){
            TextColor(RED);
            printf("\nERROR: El nombre de la variable solo puede tener caracteres alfanumericos.\n");
            return 0;
        }
    }

    //Comprobar que no coincida con ningun comando.
    for(int i = 0; i < longitudArr; i++){
        if(strcmp(entrada, palabrasABuscar[i]) == 0){
            TextColor(RED);
            printf("\nERROR: El nombre de la variable no puede ser el mismo que un comando.\n");
            return 0;
        }
    }

    return 1;
}


/**
 * Busca la variable dentro de la lista de variables.
 * @param Lista Es un puntero a la lista(TVars).
 * @param Entrada Es una cadena con el nombre de la variable a buscar.
 * @return La variable si ha sido encontrada, en otro caso NULL.
*/
TVar *buscarVariable(TVars const *lista, char *entrada)
{
    if(lista->primera == NULL){
        return NULL;
    }

    TVar *actual = lista->primera;

    //Iterar sobre la lista y comparar los nombres.
    while(actual != NULL){
        if(strcmp(actual->nomVar, entrada) == 0){
            return actual;
        }
        actual = actual->sig;
    }
    return NULL;
}


/**
 * Crear una copia TMatriz a partir de otra TMatriz, pero sin copiar los
 * valores de la matriz bidimensional.
 * @param Matriz Es el puntero a la TMatriz que se va a copiar.
 * @return La copia de la TMatriz o NULL en caso de error al reservar la memoria.
*/
TMatriz *crearCopiaMatriz(TMatriz *matriz)
{
    TMatriz *matrizCopia = malloc(sizeof(TMatriz));
    if(matrizCopia == NULL){
        return NULL;
    }

    //Copiar filas y columnas.
    matrizCopia->f = matriz->f;
    matrizCopia->c = matriz->c;

    //Crear una matriz bidimensional con las dimensiones de la TMatriz copiada.
    matrizCopia->m = crearMatriz(matrizCopia->f, matrizCopia->c);
    if(matrizCopia->m == NULL){
        free(matrizCopia);
        matrizCopia = NULL;
        return NULL;
    }

    return matrizCopia;
}

/**
 * Crea una copia entera de una TMatriz, incluso copia los valor de la matriz original.
 * @param MatrizACopiar Es un puntero de la TMatriz que se va a a copiar.
 * @return La copia exacta de TMatriz o NULL en caso de error al reservar la memoria.
*/
TMatriz *copiarMatriz(TMatriz *matrizACopiar)
{
    //Crear una copia de la original.
    TMatriz *matrizCopia = crearCopiaMatriz(matrizACopiar);
    if(matrizCopia == NULL){
        return NULL;
    }

    //Copiar los valores de la matriz bidimensional.
    for(int i = 0; i < matrizCopia->f; i++){
       for(int j = 0; j < matrizCopia->c; j++){
            matrizCopia->m[i][j] = matrizACopiar->m[i][j];
        }
    }
    
    return matrizCopia;
}

/**
 * Actualiza las dimensiones y los valores de la matriz bidimensional de una
 * TMatriz a partir de otra TMatriz.
 * @param MatrizOG Es un puntero a una TMatriz que va a ser actualizada.
 * @param MatrizACopiar Es un puntero a una TMatriz que va a ser copiada.
*/
void actualizarMatriz(TMatriz *matrizOg, TMatriz *matrizACopiar)
{   
    //Verificar que no son iguales para evitar una copia innecesaria.
    if(matrizOg != matrizACopiar){
        //Liberar la matriz original.
        liberarMat(matrizOg);

        matrizOg = crearCopiaMatriz(matrizACopiar);
        if(matrizOg == NULL){
            return;
        }

        for(int i = 0; i < matrizOg->f; i++){
            for(int j = 0; j < matrizOg->c; j++){
                matrizOg->m[i][j] = matrizACopiar->m[i][j];
            }
        }
    }  
}

/**
 * Establece todos los elementos de la matriz bidimensional de una TMatriz a 0.
 * @param Matriz Es el puntero TMatriz a ser inicializado a 0.
*/
void ondaCero(TMatriz *matriz)
{
    for(int i = 0; i < matriz->f; i++){
        for(int j = 0; j < matriz->c; j++){
            matriz->m[i][j] = 0;
        }
    }
}

/**
 * Lee la entrada y verifica si es una matriz explicita o una variable.
 * @param Entrada Es la cadena a ser analizada.
 * @param Lista Es el puntero a TVars, es decir la lista.
 * @return Un puntero a TMatriz ya sea creado explícitamente o a partir de una variable. 
 * En caso de no ser ninguno se devolverá NULL.
*/
TMatriz *VarOMat(char *entrada, TVars *lista)
{
    //Si no se puede crear una matriz explicita se prueba a ver si se trata de una variable.
    TMatriz *nuevaMatriz = crearMatrizExplicita(entrada);
    if(nuevaMatriz == NULL){
        TVar *nuevaVariable = buscarVariable(lista, entrada);
        if(nuevaVariable != NULL){
            nuevaMatriz = nuevaVariable->mat;
            return nuevaMatriz;
        }
        else{
            //Si no es ninguno de los 2 casos devolvemos:
            return NULL;
        }
    }
    return nuevaMatriz;
}

/**
 * Lee la entrada y verifica si es una matriz explicita o una variable y además activa la propiedad liberar si es explícita.
 * @param Entrada Es la cadena a ser analizada.
 * @param Lista Es el puntero a TVars, es decir la lista.
 * @return Un puntero a TMatriz ya sea creado explícitamente o a partir de una variable. 
 * En caso de no ser ninguno se devolverá NULL.
*/
TMatriz *VarOMatliberarMemoria(char *entrada, TVars *lista)
{
    TMatriz *nuevaMatriz = crearMatrizExplicita(entrada);
    if(nuevaMatriz == NULL){
        TVar *nuevaVariable = buscarVariable(lista, entrada);
        if(nuevaVariable != NULL){
            nuevaMatriz = nuevaVariable->mat;
            return nuevaMatriz;
        }
        else{
            return NULL;
        }
    }
    nuevaMatriz->liberar = 1;
    return nuevaMatriz;
}

/**
 * Realiza la operación de producto entre un escalar y un matriz o una escalar y una variable.
 * Además puede realizarlo en cualquier orden.
 * @param Entrada Es al cadena que pertenece al comando product.
 * @param Lista Puntero a la estructura TVars, es decir a la lista.
 * @return Puntero a la nueva matriz  resultante de la operacion o NULL si ha habido algún error.
*/
TMatriz *VarOMatPE(char *entrada, TVars *lista)
{
    char *token, *tokenAux;
    double escalar;
    TMatriz *matrizAux, *matrizAux2;
    TVar *varAux;

    Trim(entrada);

    tokenAux = strdup(entrada);
    token = localizarMatriz(tokenAux);

    //Si no hay matriz explícita buscaremos la variable.
    if(token == NULL){
        token = strtok(tokenAux, "\t ");
        varAux = buscarVariable(lista, token);
        if(varAux == NULL){
            if(es_numero(token) == 0){
                free(tokenAux);
                tokenAux = NULL;
                TextColor(RED);
                printf("\nERROR en sintaxis del comando product.\nAsegurese de escribirlo en el formato: 'product n mat' o 'product mat n'.\n");
                return NULL;
            }
            //free(tokenAux);
            strcpy(tokenAux, token);
            token = strtok(NULL, "\t ");
            varAux = buscarVariable(lista, token);
            if(varAux == NULL){
                free(tokenAux);
                tokenAux = NULL;
                TextColor(RED);
                printf("\nERROR en sintaxis del comando product.\nLa variable no existe en la lista.\n");
                return NULL;
            }
        }
        else{
            tokenAux = strtok(NULL, "\t ");
            if(es_numero(tokenAux) == 0){
                free(tokenAux);
                tokenAux = NULL;
                TextColor(RED);
                printf("\nERROR en sintaxis del comando product.\nEl escalar introducido no es valido.\n");
                return NULL;
            }
        }
        token = strtok(NULL, "\t ");
        if(token != NULL){
            free(tokenAux);
            tokenAux = NULL;
            TextColor(RED);
            printf("\nERROR en sintaxis del comando product.\nSobran argumentos en el comando.\n");
            return NULL;
        }
        matrizAux = varAux->mat;
    }
    else{
        Trim(token);
        Trim(tokenAux);
        matrizAux = crearMatrizExplicita(token);
        if(matrizAux == NULL){
            free(tokenAux);
            tokenAux = NULL;
            token = NULL;
            TextColor(RED);
            printf("\nERROR en sintaxis del comando product.\nNo ha sido posible leer la matriz explicita.\n");
            return NULL;
        }
        //Solo si es una matriz explicita se activará liberar.
        matrizAux->liberar = 1;
        if(es_numero(tokenAux) == 0){
            free(tokenAux);
            token = NULL;
            tokenAux = NULL;
            liberarMat(matrizAux);
            TextColor(RED);
            printf("\nERROR en sintaxis del comando product.\nEl escalar introducido no es valido.\n");
            return NULL;
        }
    }
    escalar = atof(tokenAux);
    matrizAux2 = productoEscalar(matrizAux, escalar);
    if(matrizAux->liberar == 1){
        liberarMat(matrizAux);
    }
    free(tokenAux);
    free(token);
    token = NULL;
    tokenAux = NULL;

    return matrizAux2;
}

/**
 * Crea una TMatriz que es el resultado de la suma de otras 2. 
 * Recuerdo: Las 2 matrices deben tener las mismas dimensiones.
 * @param Matriz1 Primer puntero de TMatriz.
 * @param Matriz2 Segundo puntero de TMatriz.
 * @return La TMatriz resultante de la suma o NULL en caso de que las matrices no sean de las mismas dimensiones.
*/
TMatriz *crearMatrizSuma(TMatriz *matriz1, TMatriz *matriz2)
{
    //Comparar Dimensiones
    if (compararMatrices(matriz1, matriz2) == 1){
        //Crear una copia.
        TMatriz *nuevaMatriz = crearCopiaMatriz(matriz1);
        if(nuevaMatriz == NULL){
            TextColor(RED);
            printf("\nERROR: No se ha podido reservar la memoria para la matriz.\n");
            return NULL;
        }

        //Rellenar la copia para que no ocurra nada raro.
        ondaCero(nuevaMatriz);

        //Operación de suma:
        for(int i = 0; i < nuevaMatriz->f; i++){
            for(int j = 0; j < nuevaMatriz->c; j++){
                nuevaMatriz->m[i][j] = matriz1->m[i][j] + matriz2->m[i][j];            
            }
        }
        return nuevaMatriz;
    }
    TextColor(RED);
    printf("\nERROR: No se pueden sumar matrices que no sean de las mismas dimensiones.\n");
    return NULL;
}

/**
 * Imprime el resultado de la suma de 2 matrices.
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
*/
void imprimirSumaMatrices(TMatriz *matriz1, TMatriz *matriz2)
{
    //Hacemos la suma:
    TMatriz *matrizSuma = crearMatrizSuma(matriz1, matriz2);
    if(matrizSuma == NULL){
        return;
    }

    //Imprimimos el resultado:
    imprimirMatriz(matrizSuma);

    //Liberamos:
    liberarMat(matrizSuma);
}

/**
 * Crea una TMatriz que es el resultado de la resta de otras 2. 
 * Recuerdo: Las 2 matrices deben tener las mismas dimensiones.
 * @param Matriz1 Primer puntero de TMatriz.
 * @param Matriz2 Segundo puntero de TMatriz.
 * @return La TMatriz resultante de la resta o NULL en caso de que las matrices no sean de las mismas dimensiones.
*/
TMatriz *crearMatrizResta(TMatriz *matriz1, TMatriz *matriz2)
{
    //Comparar Dimensiones
    if (compararMatrices(matriz1, matriz2) == 1){
        //Creamos una copia
        TMatriz *nuevaMatriz = crearCopiaMatriz(matriz1);
        if(nuevaMatriz == NULL){
            TextColor(RED);
            printf("\nERROR: No se ha podido reservar la memoria para la matriz.\n");
            return NULL;
        }

        //Rellenar la copia para que no ocurra nada raro.
        ondaCero(nuevaMatriz);

        //Operacion de resta:
        for(int i = 0; i < nuevaMatriz->f; i++){
            for(int j = 0; j < nuevaMatriz->c; j++){
                nuevaMatriz->m[i][j] = matriz1->m[i][j] - matriz2->m[i][j];            
            }
        }
        return nuevaMatriz;
    }
    TextColor(RED);
    printf("\nERROR: No se pueden restar matrices que no sean de las mismas dimensiones.\n");
    return NULL;
}

/**
 * Imprime el resultado de la resta de 2 matrices.
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
*/
void imprimirRestaMatrices(TMatriz *matriz1, TMatriz *matriz2)
{   
    //Hacemos la resta:
    TMatriz *matrizResta = crearMatrizResta(matriz1, matriz2);

    if(matrizResta == NULL){
        return;
    }

    //Imprimimos el resultado:
    imprimirMatriz(matrizResta);

    //Liberamos
    liberarMat(matrizResta);
}

/**
 * Crea una TMatriz que es el resultado de la multiplicación de otras 2. 
 * Recuerdo: Las columnas de la 1º matriz deben ser iguales que las filas de la 2º.
 * @param Matriz1 Primer puntero de TMatriz.
 * @param Matriz2 Segundo puntero de TMatriz.
 * @return La TMatriz resultante de la multiplicación o NULL en caso de que las matrices no sean de las mismas dimensiones.
*/
TMatriz *crearMatrizMultiplicacion(TMatriz *matriz1, TMatriz *matriz2)
{     
    double num;
    //Comparaciones
    if (compararFilasYColumnas(matriz1, matriz2) == 1){
        //Crear una TMatriz con las dimensiones correspondientes.
        TMatriz *nuevaMatriz = crearTMatriz(matriz1->f, matriz2->c);
        if(nuevaMatriz == NULL){
            TextColor(RED);
            printf("\nERROR: No se ha podido reservar la memoria para la matriz.\n");
            return NULL;
        }

        //ONDA CEROOOO
        ondaCero(nuevaMatriz);

        //Operación de la multipliación:
        for(int i = 0; i < nuevaMatriz->f; i++){
            for(int j = 0; j < nuevaMatriz->c; j++){
                num = 0;
                for(int k = 0; k < matriz1->c; k++){
                    num += matriz1->m[i][k] * matriz2->m[k][j];
                    
                }
                nuevaMatriz->m[i][j] = num;               
            }
        }
        
        return nuevaMatriz;
    }
    TextColor(RED);
    printf("\nERROR: No se pueden multiplicar matrices donde las columnas\nde la matriz 1 no coincidan con las filas de la matriz 2.\n");
    return NULL;
}

/**
 * Imprime el resultado de la multiplicación de 2 matrices.
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
*/
void imprimirMultiplicacionMatrices(TMatriz *matriz1, TMatriz *matriz2)
{
    //Hacemos la multiplicación:
    TMatriz *matrizMulti = crearMatrizMultiplicacion(matriz1, matriz2);
    if(matrizMulti == NULL){
        return;
    }

    //Imprimimos el resultado:
    imprimirMatriz(matrizMulti);

    //Liberamos:
    liberarMat(matrizMulti);
}

/**
 * Calcula y muestra el producto escalar entre 2 matrices.
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
*/
void productoEscalar2Mat(TMatriz *matriz1, TMatriz *matriz2)
{
    double resultado;

    //Verificamos si son compatibles.
    if(compararMatricesPE(matriz1, matriz2) == 0){
        return;
    }
    //Hacemos el calculo del producto escalar:
    for(int i = 0; i < matriz1->c; i++){
        resultado += matriz1->m[0][i] * matriz2->m[0][i];
    }
    TextColor(GREEN);
    printf("\nEl resultado del producto escalar entre las 2 matrices es: %.2f\n", resultado);
}

/**
 * Compara dos matrices para verificar si tienen las mismas dimensiones.
 *
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
 * @return 1 si las matrices tienen las mismas dimensiones, 0 en caso contrario.
 */
int compararMatrices(TMatriz *matriz1, TMatriz *matriz2)
{
    if(matriz1->c == matriz2->c && matriz1->f == matriz2->f){
        return 1;
    }
    else{
        return 0;
    }
}

/**
 * Compara el número de columnas de la primera matriz con el número de filas de la segunda matriz.
 *
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
 * @return 1 si el número de columnas de la primera matriz es igual al número de filas de la segunda matriz, 0 en caso contrario.
 */
int compararFilasYColumnas(TMatriz *matriz1, TMatriz *matriz2)
{
    if(matriz1->c == matriz2->f){
        return 1;
    }
    else{
        return 0;
    }
}

/**
 * Compara dos matrices para su uso en el producto escalar.
 *
 * @param matriz1 Puntero a la primera matriz.
 * @param matriz2 Puntero a la segunda matriz.
 * @return 1 si las matrices cumplen con las condiciones para el producto escalar, 0 en caso contrario.
 */
int compararMatricesPE(TMatriz *matriz1, TMatriz *matriz2)
{
    //Primero ver si son de 1 fila.
    if(es_UnaFila(matriz1) == 0 || es_UnaFila(matriz2) == 0){
        TextColor(RED);
        printf("\nERROR: Las matrices no deben tener mas de una fila.\n");
        return 0;
    }
    //Como tienen las mismas filas(1) comprobamos si tienen las mismas columnas.
    if(compararMatrices(matriz1, matriz2) == 0){
        TextColor(RED);
        printf("\nERROR: Las matrices deben tener las mismas columnas.\n");
        return 0;
    }
    return 1;
}

/**
 * Verifica si la matriz tiene solo una fila.
 *
 * @param matriz Puntero a la matriz a verificar.
 * @return 1 si la matriz tiene solo una fila, 0 en caso contrario.
 */
int es_UnaFila(TMatriz *matriz)
{
    if(matriz->f == 1){
        return 1;
    }
    else{
        return 0;
    }
}

/**
 * Verifica si la matriz es cuadrada.
 *
 * @param matriz Puntero a la matriz a verificar.
 * @return 1 si la matriz es cuadrada, 0 en caso contrario.
 */
int es_MatrizCuadrada(TMatriz *matriz)
{
   if(matriz->c == matriz->f){  
        return 1;
   }
   else{
    return 0;
   } 
}

/**
 * Calcula el determinante de una matriz cuadrada mediante el método de cofactores.
 *
 * @param matriz La matriz cuadrada de la cual se desea calcular el determinante.
 * @param FyC Número de filas o columnas de la matriz, da igual cual se pase pues deben ser iguales.
 * @return El determinante de la matriz.
 */
double det(double **matriz, int FyC)
{
    double aux = 0;
    double determinante = 0;
    int c;

    // Caso base: matriz de 1x1
    if(FyC == 1){
        determinante = matriz[0][0];
        return determinante;
    }
    // Caso base: matriz de 2x2
    else if(FyC == 2){
        determinante = ((matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]));
        return determinante;
    }
    // Caso general: matriz de tamaño mayor a 2x2
    else{
        for(int i = 0; i < FyC; i++){
            // Calcular el cofactor para cada elemento de la primera fila
            double **menor = (double**)malloc((FyC - 1) * sizeof(double*));
            if(menor == NULL){
                TextColor(RED);
                printf("\nERROR: No se ha podido reservar memoria para realizar el calculo.\n");
                //return;
            }
            for(int j = 0; j < (FyC - 1); j++){
                menor[j] = (double*)malloc((FyC - 1) * sizeof(double));
                if(menor == NULL){
                    liberarMatriz(menor, FyC - 1);
                    TextColor(RED);
                    printf("\nERROR: No se ha podido reservar memoria para realizar el calculo.\n");
                    //return;
                }
            }
            // Generar la matriz menor eliminando la fila y columna del elemento actual
            for(int x = 1; x < FyC; x++){
                c = 0;
                for(int y = 0; y < FyC; y++){
                    if(y != i){
                        menor[x-1][c] = matriz[x][y];
                        c++;
                    }
                }
            }
            // Calcular el determinante para la matriz menor, así que para ello usaremos la recursividad.
            double exp = pow(-1, 1 + i + 1);
            double dett = det(menor, FyC - 1);

            //Acumulamos el resultado de los determinantes de los cofactores.
            aux = matriz[0][i] * exp * dett;
            determinante += aux;

            //Liberamos cofactor.
            liberarMatriz(menor, FyC - 1);
        }
        return determinante;
    }
}

/**
 * Imprime la matriz resultante de multiplicar una matriz por un escalar.
 *
 * @param entrada La entrada que contiene el nombre de la variable o la matriz seguido por el escalar.
 * @param lista La lista de variables donde buscar la matriz o variable.
 */
void imprimirProdEscalar(char *entrada, TVars *lista)
{   
    //Calcular el producto escalar:
    TMatriz *matrizAux = VarOMatPE(entrada, lista);
    if(matrizAux == NULL){
        return;
    }
    
    imprimirMatriz(matrizAux);  //Imprimir Resultado
    
    liberarMat(matrizAux);      //Liberar matriz.
}

/**
 * Multiplica una matriz por un escalar y devuelve la matriz resultante.
 *
 * @param matriz La matriz que se multiplicará por el escalar.
 * @param escalar El escalar por el cual se multiplicará la matriz.
 * @return La matriz resultante de la multiplicación por el escalar, o NULL si hay un error de memoria.
 */
TMatriz *productoEscalar(TMatriz *matriz, double escalar)
{
    //Crear una TMatriz con las mismas dimensiones.
    TMatriz *matrizAux = crearTMatriz(matriz->f, matriz->c);
    if(matrizAux == NULL){
        TextColor(RED);
        printf("\nERROR: No se ha podido reservar memoria para la matriz.\n");
        return NULL;
    }

    // Multiplicar cada elemento de la matriz original por el escalar y asignar a la nueva matriz
    for(int i = 0; i < matrizAux->f; i++){
        for(int j = 0; j < matrizAux->c; j++){
            matrizAux->m[i][j] = matriz->m[i][j] * escalar;
        }
    }

    return matrizAux;
}

TMatriz *traspuesta(TMatriz *matriz)
{
    TMatriz *traspuesta = crearTMatriz(matriz->c, matriz->f);

    if(traspuesta == NULL){
        return NULL;
    }

    ondaCero(traspuesta);

    for(int i = 0; i < traspuesta->f; i++){
        for(int j = 0; j < traspuesta->c; j++){
            traspuesta->m[i][j] = matriz->m[j][i];
        }
    }

    return traspuesta;
}

void imprimirTranspuesta(TMatriz *matriz)
{
    TMatriz *transpuesta = traspuesta(matriz);
    if(transpuesta == NULL){
        printf("\nNo se ha podido crear esa matriz.\n");
        return;
    }
    imprimirMatriz(transpuesta);

    liberarMat(transpuesta);
}

/**
 * Función que concatena horizontalmente 2 matrices con el mismo numero de filas.
 * @param matriz1 Primera matriz a concatenar.
 * @param matriz2 Segunda matriz a concatenar.
 * @return La matriz que surge de la unión de la matriz1 y matriz2.
 */
TMatriz *concatenarMatrizFilas(TMatriz *matriz1, TMatriz *matriz2){
    if(matriz1->f != matriz2->f){
        TextColor(RED);
        printf("\nERROR: Solo se pueden unir matrices que tengan el mismo numero de filas.\n");
        return NULL;
    }

    int columnas = matriz1->c + matriz2->c;
    int contador;

    TMatriz *matrizConcatenada = crearTMatriz(matriz1->f, columnas);
    if(matrizConcatenada == NULL){
        TextColor(RED);
        printf("\nERROR: No ha sido posible reservar memoria.\n");
    }

    for(int i = 0; i < matrizConcatenada->f; i++){
        contador = 0;
        for(int j = 0; j < matriz1->c; j++){
            matrizConcatenada->m[i][j] = matriz1->m[i][j];
            contador++;
        }
        for(int k = 0; k < matriz2->c; k++){
            matrizConcatenada->m[i][contador] = matriz2->m[i][k];
            contador++;
        }
    }

    return matrizConcatenada;
}

/**
 * Imprime una matriz concatenada que resulta de unir horizontalmente 2 matrices con el mismo numero de filas.
 * 
 * @param matriz1 La matriz que va a estar a la izquierda.
 * @param matriz2 La matriz que va a estar a la derecha.
 */
void imprimirConcatenacion(TMatriz *matriz1, TMatriz *matriz2)
{

    TMatriz *matrizConcatenada = concatenarMatrizFilas(matriz1, matriz2);
    if(matrizConcatenada == NULL){
        return;
    }

    imprimirMatriz(matrizConcatenada);

    liberarMat(matrizConcatenada);
}

/**
 * Guarda la lista de variables en un archivo con el formato especificado.
 *
 * @param lista La lista de variables a ser guardada.
 * @param nombre El nombre del archivo en el que se guardará la lista.
 */
void save(TVars *lista, char *nombre)
{
    TVar *actual = lista->primera;
    if(actual == NULL){
        TextColor(RED);
        printf("\nNo es posible crear el archivo ya que la lista esta vacia.\n");
        return;
    }

    FILE *archiu = fopen(nombre, "w");
    if(archiu == NULL){
        //perror
        return;
    }


    while(actual != NULL){
        //Escribir el nombre de la variable en el fichero.
        fprintf(archiu, "%s\n", actual->nomVar);
        fprintf(archiu, "( ");

        //Escribir la matriz en el fichero.
        for(int i = 0; i < actual->mat->f; i++){
            for(int j = 0; j < actual->mat->c; j++){
                fprintf(archiu, "%.8f ", actual->mat->m[i][j]);
            }
            if(i == (actual->mat->f - 1)){
                fprintf(archiu, ")\n");
            }
            else{
                fprintf(archiu, "| ");
            }
        }

        //Iterar en la lista.
        actual = actual->sig;
    }
    fclose(archiu);  
}

/**
 * Borra la lista actual y la carga con la lista de variables que hay en un archivo con el formato especificado.
 *
 * @param lista La lista de variables que se actualizará con los datos del archivo.
 * @param nombre El nombre del archivo del cual se cargarán los datos.
 */
void load(TVars *lista, char *nombre)
{
    int i = 1;
    char linea[1000];
    char *nomVar = NULL;
    TMatriz *matriz = NULL;
    TVar *var = NULL;

    FILE *archiu = fopen(nombre, "r");
    if(archiu == NULL){
        TextColor(RED);
        printf("\nERROR: No se ha podido abrir el fichero.\n");
        return;
    }

    //Liberamos la lista actual.
    if(lista->primera != NULL){
        liberarLista(lista);
        lista->primera = NULL;
        lista->numVars = 0;
    }

    fgets(linea, sizeof(linea), archiu);
    eliminarSaltoLinea(linea);
    while(!feof(archiu)){ 
        //Línea impar siempre está el nombre.
        if(i % 2 != 0){
            nomVar = strdup(linea);
        }
        //Línea par siempre está el contenido de la matriz.
        else{
            matriz = crearMatrizExplicita(linea);
            if(matriz != NULL){
                var = crearVariableExplicita(matriz, nomVar);
                agregarVarAlFinalDeLista(lista, var);
            } 
            free(nomVar);
            nomVar = NULL;
        }
        fgets(linea, sizeof(linea), archiu);
        eliminarSaltoLinea(linea);
        i++;
    }
    
    if(nomVar != NULL){
        free(nomVar);
        nomVar = NULL;
    }
}

/**
 * Actualiza la lista de variables desde un archivo con el formato especificado, sin borrar las actuales.
 * Si una variable con el mismo nombre ya existe en la lista, se actualiza su matriz asociada.
 *
 * @param lista La lista de variables que se actualizará o creará con los datos del archivo.
 * @param nombre El nombre del archivo del cual se cargarán o actualizarán los datos.
 */
void loadOver(TVars *lista, char *nombre)
{
    int i = 1;
    char linea[1000];
    char *nomVar = NULL;
    TMatriz *matriz = NULL;
    TVar *nuevaVar = NULL;
    TVar *varAux = NULL; 

    FILE *archiu = fopen(nombre, "r");
    if(archiu == NULL){
        TextColor(RED);
        printf("\nERROR: No se ha podido abrir el fichero.\n");
        return;
    }
    

    fgets(linea, sizeof(linea), archiu);
    eliminarSaltoLinea(linea);
    while(!feof(archiu)){
        //Linea impar se trata del nombre:
        if(i % 2 != 0){
            nomVar = strdup(linea);
        }
        //Linea par se trata de la matriz:
        else{
            matriz = crearMatrizExplicita(linea);
            if(matriz != NULL){
                varAux = buscarVariable(lista, nomVar);
                //Si existe se libera su matriz actual y se actualiza por la del fichero.
                if(varAux != NULL){
                    liberarMat(varAux->mat);
                    varAux->mat = matriz;
                }
                //Si no existe se añade a la lista.
                else{
                    nuevaVar = crearVariableExplicita(matriz, nomVar);
                    agregarVarAlFinalDeLista(lista, nuevaVar);
            }
            }
            free(nomVar);
            nomVar = NULL;    
        }

        fgets(linea, sizeof(linea), archiu);
        eliminarSaltoLinea(linea);
        i++;
        
    }
    if(nomVar != NULL){
        free(nomVar);
        nomVar = NULL;
    }
}

void help()
{
    printf("\n########################################################################################################################################\n");
    printf("\t\t\t\t\t\tFuncionamiento del programa");
    printf("\n########################################################################################################################################\n\n");
    printf("# Tipos de matriz:\n\n");
    printf("\t > Matriz Explicita: Se escriben entre parentesis --> (1 2 3 | 4 5 6)\n");
    printf("\t > Matriz Implicita: Se escribe el nombre de la variable --> var1\n\n");
    printf("# Operaciones: \n\n");
    printf("\t > Declarar una matriz -> (1 2 3 | 4 5 6)\n");
    printf("\t\t--Los '|' se usan para separar las filas de la matriz.\n");
    printf("\t\t--Se pueden usar espacios o tabuladores para separar los elementos de la matriz.\n");
    printf("\t\t--Las matrices deben tener las mismas columnas en cada fila.\n");
    printf("\t\t--Para los numeros decimales usamos '.' .\n");
    printf("\t\t--No hace falta separacion entre los numeros y los '|' o los '()', la unica separacion necesaria es entre numeros.\n\n");
    printf("\t > Asignacion -> var1 = (1 2 3 | 4 5 6)\n");
    printf("\t\t--Crear una variable o la actualizar si ya existia previamente.\n");
    printf("\t\t--Sirve cualquier cadena alfanumerica, excepto para:\n"); 
    printf("\t\t\t-Cadenas superiores a 15 caracter\n"); 
    printf("\t\t\t-Cadenas que no empiecen por un caracter\n");
    printf("\t\t\t-Cadenas que coincidan con el nombre de algun comando.\n");
    printf("\t\t--Se puede asignar la matriz de una variable a otra: var2 = var1\n");
    printf("\t\t--En las asignaciones se pueden usar operaciones(+, - o *): \n\n");
    printf("\t\t\t var1 = m1 + m2\t\t\t var1 = (1 2 3 | 4 5 6) + var3\n");
    printf("\t\t\t var2 = m1 - var1\t\t var2 = (1 2 3 | 4 5 6) - (4 5 6 | 1 2 3)\n");
    printf("\t\t\t var3 = var1 * m2\t\t var3 = var2 * (1 2 3 | 4 5 6)\n\n");
    printf("\t\t--Solo se puede aplicar una operacion por linea.\n");
    printf("\t\t--Las operandos se pueden escribir pegados a la operacion -> var1=m1+m2\n");
    printf("\t\t--Las filas y columnas de las matrices que participen en la operacion han de adecuarse al tipo de operacion.\n\n");
    printf("\t > Imprimir -> (1 2 3 4 5) // Otra forma -> NombreVariable\n");
    printf("\t\t--Imprime por pantalla la matriz explicita o la variable escrita por el usuario\n");
    printf("\t\t--Se pueden usar las operaciones(+, - o *) para imprimir el resultado.\n");
    printf("\t\t--Cada numero se imprime con 2 decimales.\n\n");
    printf("# Operadores: \n\n");
    printf("\t > Operador '=': Realiza la operacion de asignacion. \n\t\t\t A la parte de la izquierda se le asigna el resultado de la derecha.\n");
    printf("\t > Operador '+': Realiza la suma entre 2 matrices.\n");
    printf("\t > Operador '-': Realiza la resta entre 2 matrices.\n");
    printf("\t > Operador '*': Realiza la multiplicacion entre 2 matrices.\n");
    printf("\t > Operador '$': Realiza la producto escalar entre 2 matrices.\n\t\t\t Deben ser matrices de una fila y sale como resultado un solo numero.\n");
    printf("\t > Operador '&': Realiza la union entre 2 matrices horizontalmente.\n\t\t\t Dada una M1 de [fil x col1] y M2 de [fil x col2] sale una nueva M de [fil x (col1+col2)]\n");
    printf("# Comandos: \n\n");
    printf("\t > quit\n");
    printf("\t\t -Finaliza la ejecucion del programa.\n\n");
    printf("\t > det M1\n");
    printf("\t\t -Calcula el determinante de una matriz(explicita o variable).\n\n");
    printf("\t > product n M1\n");
    printf("\t\t -Calcula el producto del escalar 'n' por la matriz M1(explicita o variable).\n");
    printf("\t\t -La operacion se puede aplicar de 2 formas -> product n M1 o product M1 n .\n");
    printf("\t\t -Esta operacion se puede usar para asignar e imprimir.\n\n");
    printf("\t > transp M1\n");
    printf("\t\t -Transpone la matriz M1(explicita o variable).\n");
    printf("\t\t -Esta operacion se puede usar para asignar e imprimir.\n\n");
    printf("\t > save nombreFichero\n");
    printf("\t\t -Guarda la lista de variables en la memoria dentro de un fichero que se especifica en el parametro\n\t\t nombreArchivo, si este fichero ya existe se elimina y se crea uno nuevo.\n");
    printf("\t\t -Para poder usar este comando al menos una variable debe estar creada.\n\n");
    printf("\t > load nombreFichero\n");
    printf("\t\t -Se lee el fichero llamado nombreFichero, en caso de lectura correcta se eliminan las variables \n\t\t existentes y se cargan las del fichero.\n\n");
    printf("\t > load over nombreFichero\n");
    printf("\t\t -Variante del comando load, que anade las variables del fichero a las variables existentes en el\n\t\t programa en caso de que alguna variable del fichero ya exista en el programa sera sustituida por la del fichero.\n\n");
    printf("\t > view\n");
    printf("\t\t -Muestra una lista con las variables, ordenadas alfabeticamente, que existen en el programa.\n");
    printf("\t\t -Detalla las dimensiones de la matriz de cada variable y muestra su primera fila.\n");
    printf("\n########################################################################################################################################\n");

}


void TextColor(int color)
{
    static int __BACKGROUND;

    HANDLE h = GetStdHandle ( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbiInfo;

    GetConsoleScreenBufferInfo(h, &csbiInfo);
    SetConsoleTextAttribute (GetStdHandle (STD_OUTPUT_HANDLE), color + (__BACKGROUND << 4));
}