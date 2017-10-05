package vonsim.webapp.tutorials

import vonsim.webapp.UIConfig

class VariablesTutorial extends Tutorial {
  val title="Variables en assembly"
  
  val initialCode="""
org 2000h
; código aquí
hlt
end
    """
  
  
  val id="variables"
  
  val steps=List(

            TutorialStep("Introducción"
,"""<p>Sabiendo ya como escribir y ejecutar programas con VonSim, y la estructura básica de un 
  programa en assembly, vamos a comenzar a ver funcionan las variables en Assembly.</p> 

<p>Recordemos que como programadores tenemos dos lugares para guardar 
información: la memoria principal o RAM y los registros.</p>
   
</p>En este tutorial veremos como definir variables en la memoria e inicializarlas.<p> 

""",UIConfig.disableAll,None
)
      

,TutorialStep("Ubicación de las variables"
,"""
<p> Al igual que las instrucciones, las variables también deben ser ubicadas con una sentencia <code>org</code>, como vimos en el tutorial anterior.</p>

<p>Por eso agregamos otra sentencia <code>org</code> al comienzo del programa, en este caso en la dirección 5h de memoria.</p>

<p> Esto quiere decir que las variables que ahora declaremos debajo de la línea <code>org 5h</code>
se ubicarán a partir de la dirección de memoria 5h, es decir 5 en hexadecimal (siempre escribiremos las direcciones en hexadecimal) </p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)

,TutorialStep("Declaración de variables"
,"""
<p> Las variables se declaran en una línea aparte, con  
la sintaxis <code>nombre tipo valor_inicial</code>.</p>

<p> Hay dos tipos de variables, las <code>db</code>, que ocupan un byte, 
y las <code>dw</code> que ocupan dos bytes.</p>

<p>Entonces, para definir una variable llamada <code>peso</code> 
que ocupe un solo byte (<code>db</code>) y tenga como valor inicial 25h (25 hexadecimal), debemos agregar la línea
<code>peso db 25h</code> debajo de la línea <code>org 5h</code> </p>

<p class="exercise"> Agrega la línea <code>peso db 25h</code> para definir la 
variable peso con valor 25h, debajo de la sentencia <code>org 5h</code>. 
Ejecuta el programa para cargar las variables en la memoria.</p>

<p class="exercise"> Busca la celda de memoria con dirección 5h. Debería tener el valor 25h. </p>

<p> Más tarde veremos como definir variables con valores decimales o binarios, pero por ahora 
lo haremos con valores hexadecimales</p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)

,TutorialStep("Valos hexadecimales"
,"""
<p> Como vimos, los valores hexadecimales requieren una <em>h</em> al final para
especificar este sistema.</p>
<p> Recuerda que tanto 5h, 25h, 1Ah, Ah o BCDEh son valores hexadecimales, ya que las letras
A, B, C, D, E y F codifican los valores decimales 10, 11, 12, 13, 14 y 15 en el sistema
hexadecimal.</p>
<p>En el caso de valores como Ah o BCDEh, que <em>comienzan</em> con una letra, el simulador
requiere que se anteponga un 0 al valor. Entonces en lugar de escribir Ah o BCDEh, escribiremos
0Ah o 0BCDEh.</p>
<p> De este modo, el simulador puede distinguir el valor Ah de una variable llamada Ah.</p>

<p class="exercise"> El programa del editor no compila. Agrega el 0 al valor A3h de la variable
<code>peso</code> para que compile y ejecútalo. ¿Qué valor aparece en la dirección 5h?</p>

""",UIConfig.enableAll,Some("org 5h\npeso db A3h\norg 2000h\nhlt\nend")
)

,TutorialStep("Orden de almacenamiento de las variables (parte 1)"
,"""
<p> La variable <code>peso</code> que declaramos se ubicó en la celda con dirección 5h.</p>

<p> ¿Qué sucede si declaramos otra variable, también de un byte, a continuación?</p>

<p class="exercise"> Agrega la línea <code>temperatura db 14h</code> 
para definir la variable temperatura con valor 14h debajo de la variable peso. 
Ejecuta el programa.</p>

<p class="exercise"> Busca la celda de memoria con dirección 5. Debería seguir teniendo el valor 25h.
Mirá la celda siguiente, con dirección 6. ¿Qué valor tiene? </p>

""",UIConfig.enableAll,Some("org 5h\npeso db 25h\n\norg 2000h\nhlt\nend")
)

,TutorialStep("Orden de almacenamiento de las variables (parte 2)"
,"""
<p> La variable <code>temperatura</code> que declaramos se ubicó 
en la celda con dirección 6. Esto es porque las variables se ubican una tras de otra 
a partir de la dirección indicada en la sentencia <code>org</code> </p>

<p class="exercise"> Intercambia las declaraciones de las variables <code>peso</code> y 
<code>temperatura</code>. Ejecuta el programa y verifica que ahora los valores se invierten
en la memoria, es decir, primero se ubica la variable temperatura 
y luego la variable peso.</p>

<p class="exercise"> Agrega otras dos variables de un byte llamadas <code>edad</code> y 
<code>altura</code>, con valores iniciales 3Ah y 4Ch, debajo de la variable <code>peso</code>.   
Ejecuta el programa.</p>

<p class="exercise"> Observa el valor de las celdas de memoria con dirección 7 y 8. ¿Qué valores tienen?</p>

""",UIConfig.enableAll,Some("org 5h\npeso db 25h\ntemperatura db 14h\norg 2000h\nhlt\nend")
)

,TutorialStep("Variables de dos bytes (parte 1)"
,"""
<p> Las variables que declaramos ocupaban todas un byte, ya que 
usaban el tipo <code>db</code>.</p>

<p> Podemos definir variables que ocupen 2 bytes con el tipo <code>dw</code>.
Reservando 2 bytes para la variable, podemos guardar números más grandes.</p>

<p class="exercise"> Agrega la línea <code>peso dw 5A12h</code> para definir 
la variable peso con valor 5A12h. 
Ejecuta el programa y observa el valor de las celdas 5h y 6h.</p>


<p> Habrás visto que las variables de dos bytes ocupan dos celdas de memoria, ya que cada celda de la
memoria guarda un byte. En este caso vemos que la parte menos significativa del valor (12h) se ubicó
en la celda con la dirección más chica (5h). Por otro lado, la parte más significativa (5Ah) se ubicó
en la celda con la dirección más alta (6h). Este esquema para guardar las variables se llama, por 
razones históricas, <strong>little-endian</strong>. </p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)

,TutorialStep("Variables de dos bytes (parte 2)"
,"""
<p> Podemos definir varias variables de tipo dw también, y también se ubicarán secuencialmente.</p>

<p class="exercise"> Define las variables <code>vida</code>, <code>mana</code> y <code>energía</code>, en ese orden, de tipo <code>dw</code>,
con valores iniciales 32h, 15Dh y 1A4Bh, respectivamente.</p>

<p class="exercise"> Ejecuta el programa y observa el valor de las celdas 5h a Ah.
¿Qué sucede cuando ponemos un valor chico, como 32h, en una variable de 2 bytes?
¿Cómo se rellena la parte más significativa? </p>


""",UIConfig.enableAll,Some("org 5h\n;aca van las variables\norg 2000h\nhlt\nend")
)
,TutorialStep("Variables de dos bytes (parte 3)"
,"""

<p class="exercise"> El programa del editor declara las variables del paso anterior. Ejecútalo 
nuevamente y observa en qué dirección comienza cada variable.</p>


<p> En este caso, la variable <code>vida</code> empieza en la dirección 5h;
la variable <code>mana</code> en la dirección 7h y la variable <code>energia</code> 
en la dirección 9h.</p>

<p> Por ende la variable <code>vida</code> ocupa las celdas 5h y 6h;
la variable <code>mana</code> ocupa las celdas 7h y 8h y la variable <code>energia</code> 
las celdas 9h y 10h.</p>

<p class="exercise"> Si definieramos una nueva variable debajo de <code>energia</code>,
¿en qué dirección de memoria comenzaría?</p>

""",UIConfig.enableAll,Some("org 5h\nvida dw 32h\nmana dw 15Dh\nenergia dw 1A4Bh\norg 2000h\nhlt\nend")
)


,TutorialStep("Ubicación de las variables con <code>db</code> y <code>dw</code> "
,"""
<p> Hemos definido varias variables de uno y dos bytes por separado. ¿Qué sucede si las combinamos?</p>


<p class="exercise"> Ejecuta el programa del editor, en donde se definen distintos tipos de variables.</p>

<p class="exercise"> ¿Cuál es la dirección de comienzo de cada variable? ¿Qué celdas de memoria ocupa cada variable? </p>

""",UIConfig.enableAll,Some("org 5h\nprecipitaciones dw 134h\nnubes db 45h\ntemperatura dw 2Ah\nviento db 8Ah\norg 2000h\nhlt\nend")
)

,TutorialStep("El rol del <code>org</code>"
,"""
<p> Hasta ahora las variables que definimos se ubicaban a partir de la dirección 5h,
debido a que estaban debajo de un <code> org 5h</code></p>

<p class="exercise"> Cambia el 5h en la línea <code> org 5h</code> por 12h. Ejecuta el programa.
Verifica que las variables ahora se ubican a partir de la dirección 12h.</p>

""",UIConfig.enableAll,Some("org 5h\nprecipitaciones dw 134h\nnubes db 45h\ntemperatura dw 2Ah\nviento db 8Ah\norg 2000h\nhlt\nend")
)

,TutorialStep("Utilizando varios <code>org</code>"
,"""
<p>¿Qué podemos hacer si queremos que algunas variables se ubican a partir del 5h, y
otras a partir del 12h? Utilizamos dos sentencias <code>org</code>.</p>

<p class="exercise"> Lee y ejecuta el programa del editor. 
¿Qué direcciones tienen las variables <code>precipitaciones</code> y <code>nubes</code>? 
¿Y las variables <code>temperatura</code> y <code>viento</code>?</p>

<p>Entonces en este caso definimos tres sectores de memoria para nuestro programa:
el primero para las variables <code>precipitaciones</code> y <code>nubes</code>, a partir de la dirección 5h, 
el segundo paralas variables <code>temperatura</code> y <code>viento</code>, a partir de la dirección 12h,
y el tercero para el código, a partir de la dirección 2000h.</p>
""",UIConfig.enableAll,Some("org 5h\nprecipitaciones dw 134h\nnubes db 45h\norg 12h\ntemperatura dw 2Ah\nviento db 8Ah\norg 2000h\nhlt\nend")
)

,TutorialStep("Valores decimales para la dirección del <code>org</code>"
,"""
<p>En algunos casos puede ser más fácil especificar la dirección de memoria en decimal.
Supongamos que queremos ubicar variables a partir de la dirección de memoria 12. En tal caso,
en lugar de tener que convertirla a hexadecimal, podemos escribir el 12 sin la <em>h</em> en
la instrucción <code>org</code>.</p>

<p class="exercise"> Lee y ejecuta el programa del editor. Las variables se ubican a partir
de la dirección 12h.</p>

<p class="exercise"> Quita el <em>h</em> de la sentencia <code>org 12h</code> y ejecuta
el programa. ¿Dónde se ubican las variables ahora?</p> 
<p class="answer">Las variables se ubican a partir de la dirección 12, o sea Bh.</p>
""",UIConfig.enableAll,Some("org 12h\ntemperatura dw 2Ah\nviento db 8Ah\norg 2000h\nhlt\nend")
)


,TutorialStep("Valores decimales para inicializar las variables"
,"""
<p> Si bien la memoria muestra los valores de las celdas en formato hexadecimal, debido a
que es lo más común, en verdad lo que se guarda en cada celda son 8 bits, un byte,
que codifican un número utilizando el sistema binario. </p>

<p> Hasta ahora hemos inicializado las variables con un valor codificado en hexadecimal, pero al
cargarse en la memoria en verdad se guarda en formato binario.</p> 

<p>Entonces, en realidad el formato hexadecimal es solo una conveniencia para 
escribir los valores de forma más cómoda.</p>

<p> También podemos escribirlos con un valor codificado en decimal, como hicimos con la 
dirección del <code>org</code>. 
Para ello, recordemos que simplemente debemos no poner una <em>h</em> al final del valor.</p>

<p class="exercise"> Agrega la línea <code>peso db 25</code> para definir la variable peso con valor 25 (decimal). 
Ejecuta el programa y busca el valor de la celda de memoria donde se cargó</p>

<p class="exercise"> Ese valor, ¿es 25? ¿por qué no? ¿con qué codificación se está mostrando?</p>

<p class="answer">Se muestra el valor 19h, porque se muestra en hexadecimal <p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)

,TutorialStep("Valores máximos"
,"""

<p>Las variables de tipo <code>db</code> tienen un rango de 0 a 255 para valores sin signo,
 ya que disponen de 8 bits.</p>

<p class="exercise"> Intenta poner un valor mayor a 255 en la variable edad. ¿Qué sucede? </p>

<p>Las variables de tipo <code>dw</code> tienen un rango de 0 a 65536 para valores sin signo,
 ya que disponen de 16 bits.</p>

<p class="exercise"> Intenta poner un valor mayor a 65536 en la variable distancia. ¿Qué sucede? </p>

<p> En ambos casos, como son valores positivos, se codifican en el sistema Binario Sin Signo 
(BSS) al guardarse en la memoria.</p>

""",UIConfig.enableAll,Some("org 5h\nedad db 50\ndistancia dw 1529\norg 2000h\nhlt\nend")
)

,TutorialStep("Valores negativos"
,"""

<p>También se pueden usar valores negativos para inicializar una variable. </p>

<p class="exercise"> Prueba poniendo el valor -10 a la variable temperatura y ejecutando
el programa. ¿Qué se almacena en la memoria en la dirección 5h? ¿Por qué?</p>

<p class="answer"> Se almacena el valor F6h, o sea 11110110b, que es la codificación
en Complemento a 2 (CA2) del número -10. Hay que tener en cuenta que tanto el número
119 como el número -10 se codifican como 11110110b. Por ende es el programador quien
debe saber de antemano como interpretar esa cadena de bits, si en CA2 o en BSS. </p>

""",UIConfig.enableAll,Some("org 5h\ntemperatura db 10\norg 2000h\nhlt\nend")
)

,TutorialStep("Valores mínimos"
,"""

<p>Como se utiliza el sistema CA2 para los números negativos, el valor
mínimo para las variables de tipo <code>db</code> es de -128.</p>

<p class="exercise"> Intenta poner un valor menor a -128 en la variable edad. ¿Qué sucede? </p>

<p>Por otro lado, las variables de tipo <code>dw</code> tienen como valor mínimo el -32768.</p>

<p class="exercise"> Intenta poner un valor menor a -32768 en la variable distancia. ¿Qué sucede? </p>

""",UIConfig.enableAll,Some("org 5h\nedad db -15\ndistancia dw -1234\norg 2000h\nhlt\nend")
)

,TutorialStep("Valores binarios para inicializar las variables"
,"""

<p> Podemos también ingresar un byte en formato binario agregando una <em>b</em> al final del mismo.</p>

<p class="exercise"> Agrega debajo de <code>peso</code> la línea <code>peso db 00101001b</code> para definir la variable peso con valor 29h. 
Ejecuta el programa y verifica que la celda de memoria con dirección 6h tiene el valor 29h.</p>

<p> Recuerda que el valor 00101001b representa el valor 41 en BSS, o 29h en hexadecimal </p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)

,TutorialStep("Variables sin valor"
,"""

<p>También podemos declarar variables sin valor. Para ello ponemos <em>?</em> en lugar del valor.</p>

<p class="exercise"> Define la variable <code>peso</code> de tipo <code>db</code> con valor <em>?</em>.
Antes de ejecutar el código, anota el valor de la celda de memoria 5h. Luego ejecuta el código. ¿Qué
valor tiene ahora esta celda? </p>

""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)


,TutorialStep("Vectores de <code>db</code>"
,"""
<p>También puedes declarar una variable una variable con varios valores, es decir, un vector.
 En ese caso la sintaxis es <code>nombre tipo valor1, valor2, valor3, ...</code> </p>
<p> Los valores se guardan uno seguido del otro en la memoria.</p>

<p class="exercise">Leer y ejecutar el código del editor. ¿En qué celdas de memoria se guardan
los valores? ¿Cuántas celdas ocupan en total? </p>

<p class="answer"> Ocupan 6 celdas, una por elemento; la 5h, 6h, 7h, 8h, 9h y Ah.</p>

""",UIConfig.enableAll,Some("org 5h\ntabla db 1,3,5,7,9,11\norg 2000h\nhlt\nend")
)

,TutorialStep("Vectores de <code>dw</code>"
,"""
<p>Recién definimos un vector donde cada elemento era de tipo <code>db</code>.</p>
<p> Si ahora los elementos son de tipo <code>dw</code> entonces cada elemento ocupará
dos bytes de memoria.</p>

<p class="exercise">Ejecutar el código del editor. ¿En qué celdas de memoria se guardan
los valores? ¿Cuántas celdas ocupan en total? </p>

<p class="answer"> Ocupan 12 celdas, dos por cada elemento; de la 5h a la 10h.</p>

""",UIConfig.enableAll,Some("org 5h\ntabla dw 1,3,5,7,9,11\norg 2000h\nhlt\nend")
)

,TutorialStep("Cadenas de caracteres"
,"""
<p>También se pueden declarar strings o cadenas de caracteres. Recuerda que los caracteres en 
verdad se almacenan como códigos; los mismos se obtienen del 
<a href="https://es.wikipedia.org/wiki/ASCII#Caracteres_imprimibles_ASCII">estándar ASCII</a>.</p>
<p> Por ejemplo, la letra <em>A</em> se codifica con el número 41h, y la letra <em>a</em> con el 
número 61h.</p> 
<p> Entonces, como assembly es un lenguaje de bajo nivel, en realidad lo que declararemos es un vector
de números, donde cada número es el código ASCII de un carácter.</p>
<p> Por suerte, no debemos buscar e ingresar los códigos uno por uno, ya que el compilador de 
assembly nos permite ingresar un texto entre comillas y el convierte el mismo en códigos.</p>

<p class="exercise"> Lee y ejecuta el código del editor. ¿Qué se guarda a partir de la dirección 5h?
Verifica que los códigos corresponden a los de los caracteres h, o, l, y a. </p>

<div class="answer"> <p>Se almacenan:</p>
<ul>
<li>En la celda 5h, el valor 68h, código del carácter h</li>
<li>En la celda 6h, el valor 6Fh, código del carácter o</li>
<li>En la celda 7h, el valor 6Ch, código del carácter l</li>
<li>En la celda 8h, el valor 61h, código del carácter a</li>
</ul>
</div>

""",UIConfig.enableAll,Some("org 5h\ncadena db \"hola\"\norg 2000h\nhlt\nend")
)

,TutorialStep("Las cadenas de caracteres son sólo de tipo <code>db</code>"
,"""
<p>Las cadenas de caracteres sólo pueden ser de tipo <code>db</code>.</p>

<p class="exercise"> Modifica el código del editor, reemplazando el tipo <code>db</code> por <code>dw</code>.
¿Qué sucede? </p>

<p class="answer"> El compilador detecta el error y no compila el programa.
</p>

""",UIConfig.enableAll,Some("org 5h\ncadena db \"hola\"\norg 2000h\nhlt\nend")
)

,TutorialStep("Variables vs Etiquetas "
,"""
<p>Cuando tenemos una variable de tipo <code>dw</code>, reservamos dos celdas de memoria
para guardar un valor. Por ejemplo, en el programa del editor, las celdas 5h y 6h
contienen el valor de la variable distancia.</p>

<p>De la misma forma, cuando tenemos un vector, se utilizan varias celdas de memoria para almacenarlo.</p>

<p>Generalmente hablamos de la <strong>dirección</strong> de estas variables, pero no queda claro
a qué nos referimos, si a la dirección de la primer celda, de la última, de todas, etc.</p>

<p>Por eso, se define la <strong>dirección</strong> de una variable como la dirección de su 
primera celda, sin importar cuantas ocupe (1 para <code>db</code>, 2 para <code>dw</code>, 
o varias para un vector).</p>

<p class="exercise"> Lee y ejecuta el código del editor. ¿Cuáles son las direcciones de las variables declaradas?</p>

<div class="answer"> 
<ul>
<li>La variable <code>distancia</code> tiene dirección 5h.</li>
<li>La variable <code>amigos</code> tiene dirección 7h.</li>
<li>La variable <code>mensaje</code> tiene dirección 8h.</li>
<li>La variable <code>tablita</code> tiene dirección 16h.</li>
<li>La variable <code>androide</code> tiene dirección 22h.</li>
</ul>
</div>


""",UIConfig.enableAll,Some("""org 5h
distancia dw 14A3h
amigos db 4Ah
mensaje db "Buenas noches."
tablita dw 10,5,4Fh,1D4Ch,3h,4BCDh
androide db "R2D2"
org 2000h
hlt
end""")
)

,TutorialStep("Variables vs Etiquetas"
,"""
<p>Pensándolo de ese modo, en assembly <em>declarar una variable</em> es simplemente etiquetar una dirección de memoria.
Entonces más que una variable, tenemos una <strong>etiqueta</strong> para una celda de memoria. </p>

<p> Entonces, en realidad assembly no nos da un mecanismo para definir <strong>variables</strong> como
las entendemos en otros lenguajes de programación.
En cambio, tenemos un mecanismo para <strong>inicializar celdas de memoria</strong> con algún valor y 
<strong>etiquetar celdas de memoria</strong> con un nombre. 
</p>

""",UIConfig.enableAll,Some("org 5h\ndw 1A3Bh\norg 2000h\nhlt\nend")
)

//,TutorialStep("Variables sin etiquetas TODO"
//,"""
//
//
//<p class="exercise"> Lee y ejecuta el código del editor</p>
//
//<p class="answer"> El compilador detecta el error y no compila el programa.
//</p>
//
//""",UIConfig.enableAll,Some("org 5h\ndw 1A3Bh\norg 2000h\nhlt\nend")
//)


,TutorialStep("Dup TODO"
,"""
<p>En ocasiones, queremos definir un vector pero con todos los elementos iguales.</p>

<p class="exercise"> </p>
""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)


,TutorialStep("Resumen TODO"
,"""


<p>En resumen, en assembly se pueden etiquetar celdas de memoria e inicializar su valor,
que llamamos <strong>declarar variables</strong> aunque su significado sea algo
diferente del de otros lenguajes de programación.</p>

<p>Para ello primero debemos establecer la dirección donde se comienzan a ubicar las variables
con la sentencia <code>org</code>.</p>

<p>Luego podemos definir variables. Pueden ser de dos tipos: <code>db</code>, si ocupan un byte,
o <code>dw</code> si ocupan 2 bytes. </p>
<p>Las variables tienen una etiqueta, que luego nos servirá como referencia para accederlas o modificarlas
</p>


<p class="exercise"> </p>
""",UIConfig.enableAll,Some("org 5h\n;las variables van aqui\norg 2000h\nhlt\nend")
)


,TutorialStep("A continuación"
,"""
 <p>Ahora que sabes más sobre cómo se codifican los datos en assembly y cómo definir variables,
  puedes avanzar más con el <a href="?tutorial=simple">tutorial sobre 
  registros e instrucciones simples</a>.</p>
 
""",UIConfig.disableAll,Some(""))
)




}