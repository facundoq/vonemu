package vonsim.webapp
import vonsim.utils.CollectionUtils._
import vonsim.simulator.InstructionInfo
import com.scalawarrior.scalajs.ace._
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.annotation.JSName
import org.scalajs.dom.html._
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom
import scala.scalajs.js
import js.JSConverters._
import vonsim.simulator.Simulator
import scalatags.JsDom.all._
import scala.scalajs.js.timers.SetTimeoutHandle
import scala.scalajs.js.timers._

import vonsim.webapp
import vonsim.simulator.SimulatorProgramExecuting
import vonsim.assembly.Compiler.CompilationResult

import vonsim.assembly.Location
import vonsim.assembly.LexerError
import vonsim.assembly.ParserError
import vonsim.assembly.SemanticError
import vonsim.assembly.CompilationError
import scala.collection.mutable.ListBuffer


class EditorUI(s: VonSimState, defaultCode: String, onchange: () => Unit) extends VonSimUI(s) {

  def session=editor.getSession()
  //document.body.appendChild(div(id:="aceEditor","asdasdasdasdasd").render)
  val markers=ListBuffer[Int]()
  //val code: TextArea = textarea(cls := "textEditor").render
  //code.value = defaultCode
  val editor = webapp.myace.edit()
  //  println(editor.container)
  editor.setTheme("ace/theme/monokai")
  session.setMode("ace/mode/assembly_x86")
  editor.setValue(defaultCode)
  session.setUseSoftTabs(true)
  session.setUseWorker(false)
  editor.clearSelection()
  editor.container.classList.add("ace_editor_container")
  editor.renderer.setShowGutter(true)
  
  
  
  val container = div(id := "aceEditor"
      ,editor.container).render
  

  val root = div(id := "editor", container).render
  
  

  editor.getSession().on("change", new DelayedJSEvent(onchange).listener)

  //  container.onkeydown = (e: dom.KeyboardEvent) => {
  //    println("keydown")
  //    keyTyped()
  //  }
  def removeAllMarkers(){
    val s=editor.getSession()
    markers.foreach(m => s.removeMarker(m))
    markers.clear()
  }
  def simulatorEvent() {
    // TODO check if code can be run and if the cpu is halted to allow enable buttons    
    if (s.isSimulatorExecuting()){
      disable()
    }else{
      enable()
    }
    removeAllMarkers()
    
  }
  def simulatorEvent(i:InstructionInfo) {
    // TODO improve
    simulatorEvent()
      //"ace_active-line"
    editor.getSession()
    val r= new AceRange(i.line-1, 0, i.line-1, 1)
    val id=editor.getSession().addMarker(r, "executedLine", "fullLine",true)
    markers+=id
    //editor.getSession().addMarker(new Range(i.line, 0, i.line+1, 1), "executedLine", "fullLine",true)
  }
  
  
  def enable(){
    container.disabled=false
    root.disabled=false
    editor.setReadOnly(false)
  }
  def disable(){
    container.disabled=true
    root.disabled=true
    editor.setReadOnly(true)
  }
  
  def compilationEvent(){
    val session = editor.getSession()
    
    //mainboardUI.console.textContent=instructions.mkString("\n")
    clearGutterDecorations(session)
    s.c match {
      case Left(f) => {
        
        val annotations=instructionsToAnnotations(f.instructions)
        val globalErrorAnnotations = f.globalErrors.map(e => Annotation(0, 0, e.msg, "global_error"))
        val a = (annotations ++ globalErrorAnnotations).toJSArray
        session.setAnnotations(a)

//        println(f.instructions)
        val errors = f.instructions.lefts
        val errorLines = errors.map(_.location.line.toDouble - 1).toJSArray
        errorLines.foreach(l => session.addGutterDecoration(l, "ace_error "))
        if (!f.globalErrors.isEmpty) {
          session.addGutterDecoration(0, "ace_error ")
        }

      }
      case Right(f) => {
        val annotations = f.instructions.map(e => { Annotation(e.line.toDouble - 1, 0.toDouble, e.instruction.toString(), "Correct Instruction") })
        val warningAnnotations = f.warnings.map(w => { Annotation(w._1.toDouble - 1, 0.toDouble, w._2, "Warning") })
        
        session.setAnnotations((annotations++warningAnnotations).toJSArray)
        warningAnnotations.indices.foreach(i => session.addGutterDecoration(i.toDouble-1, "ace_warning"))
        
      }
    }
  }
  
  
  def instructionsToAnnotations(instructions:List[Either[CompilationError,InstructionInfo]])={
    instructions.map(e => {
          e match {
            case Left(LexerError(l: Location, m: String))    => Annotation(l.line.toDouble - 1, l.column.toDouble, m, "Lexer Error")
            case Left(ParserError(l: Location, m: String))   => Annotation(l.line.toDouble - 1, l.column.toDouble, m, "Parser Error")
            case Left(SemanticError(l: Location, m: String)) => Annotation(l.line.toDouble - 1, l.column.toDouble, m, "Semantic Error")
            case Right(x)                                    => Annotation(x.line.toDouble - 1, 0.toDouble, x.instruction.toString(), "Correct Instruction")
          }
        })
  }
  def clearGutterDecorations(session:IEditSession){
    (0 until session.getLength().toInt).foreach(row => session.removeGutterDecoration(row, "ace_error"))
    (0 until session.getLength().toInt).foreach(row => session.removeGutterDecoration(row, "ace_warning"))
  }
  
}



class DelayedJSEvent(val response: () => Unit) {
  var keystrokes = 0
  val listener: js.Function1[js.Any, js.Any] = (a: js.Any) => keyTyped().asInstanceOf[js.Any]

  def keyTyped() {
    keystrokes += 1
    //      println("keyTyped"+keystrokes)
    setTimeout(500)({ act() })
  }

  def act() {
    keystrokes -= 1
    //      println("act"+keystrokes)
    if (keystrokes == 0) {
      //        println("onchanged"+keystrokes)
      response()
    }
  }

}