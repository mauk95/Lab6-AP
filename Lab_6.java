/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_6;
import java.util.*;
import java.io.*;
/**
 *
 * @author mauk9_000
 */
public class Lab_6 {
 public HashMap<String,LinkedList<String>> hm;
  private Listing Dir;
  static int i = 0;
 
 private class Worker implements Runnable {
 
  private Listing queue; 
  public Worker(Listing q) {
   queue = q;
   
  }
  public void fileRead(String filePath){
        BufferedReader br = null;

        try {

                String sCurrentLine;

                br = new BufferedReader(new FileReader(filePath));

                while ((sCurrentLine = br.readLine()) != null) {
                        System.out.println(sCurrentLine);
                }

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }


 }

  public void run() {
   String name;
   while ((name = queue.remove()) != null) {
    File file = new File(name);
    String entries[] = file.list();
    if (entries == null)
     continue;
    for (String entry : entries) {
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     if(entry.endsWith(".txt")){
        String fn = name + "\\" + entry;
        if(hm.get(entry)==null){
            LinkedList<String> l1=new LinkedList<String>();
            l1.add(name);
            hm.put(entry,l1);
        }
        else{
            hm.get(entry).add(name);     
        }
        System.out.println(fn);
     }
    }
   }
  }
 }
 
 public Lab_6() {
  
  Dir = new Listing();
 }
 
 public Worker createWorker() {
  return new Worker(Dir);
 }
 
 
// need try ... catch below in case the directory is not legal
 
 
 
 
 public void processDirectory(String dir) {
   try{
   File file = new File(dir);
   if (file.isDirectory()) {
    if(hm.get(file.getName())==null){
            LinkedList<String> l1=new LinkedList<String>();
            l1.add(dir);
            hm.put(file.getName(),l1);
        }
        else{
            hm.get(file.getName()).add(dir);     
    }
    String entries[] = file.list();
    if (entries != null)
     Dir.add(dir);
 
    for (String entry : entries) {
     String subdir;
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     if (dir.endsWith("\\"))
      subdir = dir+entry;
     else
      subdir = dir+"\\"+entry;
     processDirectory(subdir);
    }
   }}catch(Exception e){}
 }
 
 public static void main(String Args[]) {
//  Scanner sc=new Scanner(System.in);
//  String a=sc.nextLine();
  String a="E:\\6th semester\\AP\\labs\\ahmed";
  Lab_6 fc = new Lab_6();
 
//  now start all of the worker threads
  fc.hm=new HashMap<String,LinkedList<String>>();
  int N = 5;
  ArrayList<Thread> thread = new ArrayList<Thread>(N);
  for (int i = 0; i < N; i++) {
   Thread t = new Thread(fc.createWorker());
   thread.add(t);
   t.start();
  }
 
//  now place each directory into the Dir
 
  fc.processDirectory(a);
 
//  indicate that there are no more directories to add
 
  fc.Dir.finish();
 
  for (int i = 0; i < N; i++){
   try {
    thread.get(i).join();
   } catch (Exception e) {};
  }
  
  
      Set set;
      set = fc.hm.entrySet();
      // Get an iterator
      Iterator i = set.iterator();
      // Display elements
      while(i.hasNext()) {
         Map.Entry me = (Map.Entry)i.next();
         System.out.print(me.getKey() + ": ");
         System.out.println(me.getValue());
      }
      System.out.println();
 }
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
        // TODO code application logic here
 //   }
    
}
