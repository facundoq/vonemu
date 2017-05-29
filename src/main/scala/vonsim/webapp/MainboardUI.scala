package vonsim.webapp
import vonsim.simulator.InstructionInfo
import vonsim.utils.CollectionUtils._
import scalatags.JsDom.all._
import org.scalajs.dom.html._
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom
import scala.scalajs.js
import js.JSConverters._
import scala.collection.mutable
import vonsim.simulator.Simulator
import scala.util.Random
import vonsim.simulator
import vonsim.simulator.Flags
import vonsim.simulator.DWord
import vonsim.simulator.Word
import vonsim.simulator.FullRegister
import scalatags.JsDom.all._
import vonsim.simulator.Flag

import vonsim.simulator.SimulatorProgramExecuting
import vonsim.simulator.SimulatorExecutionStopped
import vonsim.simulator.SimulatorExecutionError
import vonsim.simulator.SimulatorExecutionFinished
import vonsim.assembly.Compiler.CompilationResult



class MainboardUI(s: VonSimState) extends VonSimUI(s) {
  val cpuUI = new CpuUI(s)
  val memoryUI = new MemoryUI(s)
//  val ioMemoryUI = new IOMemoryUI(s)
//  val devicesUI = new DevicesUI(s)
  


  val console = pre("").render
  val consoleDir = div(id := "console",
    h2("Console"),
    console).render

  val root = div(id := "mainboard"
        ,div(id := "devices"
        ,cpuUI.root
        ,memoryUI.root
    )).render
    
   def simulatorEvent() {
    memoryUI.simulatorEvent()
    cpuUI.simulatorEvent()
    
    
  }
  def simulatorEvent(i:InstructionInfo) {
    memoryUI.simulatorEvent(i)
    cpuUI.simulatorEvent(i)

  }
  
  def compilationEvent(){
     
  }
  
}

abstract class MainboardItemUI(s: VonSimState,icon:String,itemId:String,title:String) extends VonSimUI(s){
  val contentDiv=div().render
      
  val root = div(cls:="mainboardItem",
      div(id := itemId
      ,div(cls := "flexcolumns"
        ,img(cls:= "mainboardItemIcon", src := icon), h2(title)
      )
      ,contentDiv
      )
    ).render
  
  def compilationEvent(){
     
  }
}


class MemoryUI(s: VonSimState) extends MainboardItemUI(s,"img/mainboard/ram.png","memory","Memory") {

  val body = tbody(id := "memoryTableBody", cls := "clusterize-content").render

  val memoryTable = table(cls:="table-hover",
    thead(th("Address"), th("Value")), body).render
  val memoryTableDiv = div(id := "memoryTable", cls := "memoryTable clusterize-scroll", memoryTable).render
  
  contentDiv.appendChild(memoryTableDiv)
    
  var stringRows=generateRows().toJSArray
  val clusterizePropsElements = new ClusterizeProps {
    override val rows=Some(stringRows).orUndefined
    override val scrollElem = Some(memoryTableDiv).orUndefined
    override val contentElem = Some(body).orUndefined
  }
  
  val clusterize = new Clusterize(clusterizePropsElements)
  
  def generateRows()={
    (0 until s.s.memory.values.length).map(generateRow).toArray
  }
  def generateRow(i:Int)={
    val address = formatAddress(i) 
    val value = formatWord(s.s.memory.values(i))
      s"<tr> <td> $address </td> <td> $value </td> </tr>"
  }
  
  def addressToId(address:String)={
    s"memory_address_$address"
  }
  def simulatorEvent() {
    stringRows=generateRows().toJSArray
    clusterize.update(stringRows)
  }
  def simulatorEvent(i:InstructionInfo) {
    // TODO
    simulatorEvent()
  }
  

}



