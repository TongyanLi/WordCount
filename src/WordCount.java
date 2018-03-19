import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class WordCount {
	//计数
	static int countChar = 0;
	static int countWord = 0;
	static int countLine = 0;
	//标志位
	static boolean isChar = false;
	static boolean isWord = false;
	static boolean isLine = false;
	static boolean isOut = false;
	//路径
	static String pathName = "";
	static String outputPath = "";
	
    public static void main(String[] argv){
    	while(true){
    		System.out.println("please input your command:");
	    	Scanner scanner = new Scanner(System.in);
	    	String input = scanner.nextLine();
	    	//scanner.close();
	    	if(input != null){
	    		String[] strArray = input.split("[ ]+");//以空格为分界将字符串分开成字符串数组
	    		/*如下5行注释为调试使用，其中strArray的5个下标依次代表
	    		"wc.exe","-c-w-l参数","计数文件的路径文件名","-o","输出文件路径".*/
	    		//System.out.println(strArray[0]);
	    		//System.out.println(strArray[1]);
	    		//System.out.println(strArray[2]);
	    		//System.out.println(strArray[3]);
	    		//System.out.println(strArray[4]);
	    		if(strArray[0].equals("wc.exe")){//检测输入语法正确性
	    			String[] list = strArray[1].split("-");//判断参数并设置标志位
	    			if(list.length>0){
	    				int i=1;
	    				while(i<list.length){
		    				if(strArray[2] != null)
		    					pathName = strArray[2];
		    				else
		    					System.out.println("error input!");
		    				switch(list[i]){
		    				case "c":
		    					isChar = true;
		    					break;
		    				case "w":
		    					isWord = true;
		    					break;
		    				case "l":
		    					isLine = true;
		    					break;
		    				default:
		    					System.out.println("error input!");
		    				}
		    				i++;
		    				//readFile(strArray[2]);
		    			}
	    			}else{
	    				System.out.println("error input!");
	    			}
	    			
	    			if(strArray.length > 3)//如果存在-o参数，判断并设置标志位
		    			if(strArray[3].equals("-o") && strArray[4] != null){
		    				isOut = true;
		    				outputPath = strArray[4];
		    			}
		    			else{
		    				System.out.println("error input!");
		    			}
	    		}else{
	    			System.out.println("error input!");
	    		}
	    	}else{
	    		System.out.println("please input.");
	    	}
	    	readFile(pathName);
    	}	
    	/*需要先获取命令行输入的参数，然后逐一判断，改变本地bool变量，
    	 然后调用readFile去输出相应结果*/
    	//System.out.println(argv); 	
    }
    
    /*读入需要统计的文件,根据类的本地bool变量，在readFile文件中进行判断，
    bool变量为TRUE则该输出输出该调用writeFile调用File*/
    public static void readFile(String filePath){
        try {
        	//读文件并计算数目，并写入本地变量中
            File filename = new File(filePath); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(
            		new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String str;
            while(( str=br.readLine() ) != null)//read()=-1代表数据读取完毕
            {
            	countChar += str.length();//字符个数就是字符长度
            	countChar++;//每一行会自动吞掉一个字符，所以加一
            	countWord += str.split("( |,)+").length;//split() 方法用于把一个字符串分割成字符串数组,字符串数组的长度，就是单词个数
            	countLine++;//因为是按行读取，所以每次增加一即可计算出行的数目
            }
            br.close();
            
            String content = "";
            //依照本地变量判断，并给出相应输出，并将输出结果寄存在content中
            if(isChar == true){
            	String s1 = filePath + ",字符数:" + countChar + "\r\n";
            	System.out.printf(s1);
            	content += s1;
            	isChar = false;
            }
            if(isWord == true){
            	String s2 = filePath + ",单词数:" + countWord + "\r\n";
            	System.out.printf(s2);
            	content += s2;
            	isWord = false;
            }
            if(isLine == true){
            	String s3 = filePath + ",行数:" + countLine + "\r\n";
            	System.out.printf(s3);
            	content += s3;
            	isLine = false;
            }
            if(isOut == true){
            	//组合content，然后调用writeFile
            	writeFile(content);
            	isOut = false;
            }
            
            
        }catch (Exception e) {  
            e.printStackTrace();  
        }
		//return null;
    } 
    
    //输出结果文件
    public static void writeFile(String content){
    	BufferedWriter bw = null;
	    try {  
	        File file = new File(outputPath);//将要输出的文件所在地址  
	        if (!file.exists()) {  
	            file.createNewFile();  
	        }  
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());  
	        bw = new BufferedWriter(fw);  
	        bw.write(content);  
	        bw.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
    }
}
