#ifndef _lib_h_
#define _lib_h_

#define BLACK         0
#define BLUE          1
#define GREEN         2
#define CYAN          3
#define RED           4
#define MAGENTA       5
#define BROWN         6
#define LIGHTGRAY     7
#define DARKGRAY      8
#define LIGHTBLUE     9
#define LIGHTGREEN   10
#define LIGHTCYAN    11
#define LIGHTRED     12
#define LIGHTMAGENTA 13
#define YELLOW       14
#define WHITE        15

//Estructura que representa a las matrices.
typedef struct 
{
    int f;          //Nº Filas de la matriz
    int c;          //Nº de columnas de la matriz.
    int liberar;    //Indicador para saber si se debe liberar la memoria de la matriz.
    double **m;     //Matriz de doubles.
}TMatriz;           //Representa una matriz en el programa.

//Estructura que representa a las variables.
typedef struct variable
{
    char nomVar[16];        //Nombre de la variable.
    TMatriz *mat;           //Matriz adentro de la variable.
    struct variable *sig;   //Puntero a la siguiente variable de la lista.
    struct variable *ant;   //Puntero a la anterior variable de la lista.
}TVar;                      //Representa una variable que tiene una TMatriz adentro.

//Estructura que representa la lista de las variables.
typedef struct
{
    int numVars;    //Nº de variables de la lista.
    TVar *primera;  //Puntero a la primera variable de la lista.
}TVars;             //Reptresenta la lista de variables del programa.


//Funciones para buscar en el comando de entrada del usuario:

int buscarPalabra(const char *entrada, const char *palabra);
int buscarPalabraClave(const char *entrada, const char *palabra);
char *buscarSigno(char *entrada, char *signo);
char *localizarMatriz(char *entrada);


//Funciones de creación:

double **crearMatriz(int filas, int columnas);
TMatriz *crearTMatriz(int filas, int columnas);
TMatriz *crearMatrizExplicita(char *entrada);
TVar *crearVariableExplicita(TMatriz *matriz, const char nombre[50]);
TMatriz *crearCopiaMatriz(TMatriz *matriz);
TMatriz *copiarMatriz(TMatriz *matrizACopiar);
TMatriz *crearMatrizSuma(TMatriz *matriz1, TMatriz *matriz2);
TMatriz *crearMatrizResta(TMatriz *matriz1, TMatriz *matriz2);
TMatriz *crearMatrizMultiplicacion(TMatriz *matriz1, TMatriz *matriz2);
TMatriz *productoEscalar(TMatriz *matriz, double escalar);
TMatriz *traspuesta(TMatriz *matriz);
TMatriz *concatenarMatrizFilas(TMatriz *matriz1, TMatriz *matriz2);


//Funciones de librerar memoria:

void liberarMatriz(double **matriz, int filas);
void liberarMat(TMatriz *matriz);
void liberarVariable(TVar *variable);
void liberarUnaVariable(TVars *lista, char *nombre);
void liberarLista(TVars *lista);


//Funciones para imprimir por pantalla:

void imprimirMatriz(TMatriz *matriz);
void imprimirVariable(TVar *variable);
void imprimirLista(TVars *lista);
void imprimirSumaMatrices(TMatriz *matriz1, TMatriz *matriz2);
void imprimirRestaMatrices(TMatriz *matriz1, TMatriz *matriz2);
void imprimirMultiplicacionMatrices(TMatriz *matriz1, TMatriz *matriz2);
void imprimirProdEscalar(char *entrada, TVars *lista);
void imprimirTranspuesta(TMatriz *matriz);
void imprimirConcatenacion(TMatriz *matriz1, TMatriz *matriz2);


//Funciones de cálculo:

double det(double **matriz, int filas);
void productoEscalar2Mat(TMatriz *matriz1, TMatriz *matriz2);


//Funciones para guardar/cargar ficheros:

void save(TVars *lista, char *nombre);
void load(TVars *lista, char *nombre);
void loadOver(TVars *lista, char *nombre);


//Funciones de utilidad:

void Trim(char *cadena);
void imprimirError(int eleccion);
int contarColumnas(char *token);
int es_numero(const char* token);
void eliminarParentesis(char *entrada);
int es_Vacia(char *entrada);
void leerMatrizExplicita(double **m, char *token, int contador);
void eliminarSaltoLinea(char *entrada);
int validarMatriz(char *entrada, int *filas, int*columnas);
void agregarVarAlFinalDeLista(TVars *lista, TVar *variable);
int comprobarNombre(char *entrada, char *palabrasABuscar[], int longitudArr);
int comprobarIguales(char *entrada);
TVar *buscarVariable(TVars const *lista, char *entrada);
void actualizarMatriz(TMatriz *matrizOg, TMatriz *matrizCopiar);
TMatriz *VarOMat(char *entrada, TVars *lista);
TMatriz *VarOMatliberarMemoria(char *entrada, TVars *lista);
TMatriz *VarOMatPE(char *entrada, TVars *lista);
int compararFilasYColumnas(TMatriz *matriz1, TMatriz *matriz2);
int compararMatrices(TMatriz *matriz1, TMatriz *matriz2);
int compararMatricesPE(TMatriz *matriz1, TMatriz *matriz2);
int es_UnaFila(TMatriz *matriz);
int es_MatrizCuadrada(TMatriz *matriz);
void ordenarLista(TVars* lista);
void intercambiar(TVar* a, TVar* b); 
void TextColor(int color);
void help();

#endif