/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_6;

/**
 *
 * @author mauk9_000
 */
import java.util.*;
 
public class Listing {

 private LinkedList<String> Dir;
 private boolean done;  // no more directories to be added
 private int size;  // number of directories in the queue
 public LinkedList<String> returnList(){
     return Dir;
     
 }
 
 public Listing() {
  Dir = new LinkedList<String>();
  done = false;
  size = 0;
 }
 
 public synchronized void add(String s) {
  Dir.add(s);
  size++;
  notifyAll();
 }
 
 public synchronized String remove() {
  String s;
  while (!done && size == 0) {
   try {
    wait();
   } catch (Exception e) {};
  }
  if (size > 0) {
   s = Dir.remove();
   size--;
   notifyAll();
  } else
   s = null;
  return s;
 }
 
 public synchronized void finish() {
  done = true;
  notifyAll();
 }
}