package Tools.StacksQueues;

 


import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Stack<String> stack = new Stack<String>();
        Queue<String> queue = new Queue<String>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Input: ");
        String word = sc.nextLine();
        String[] split = word.split("");
        
		for(int letter = 0; letter<split.length; letter++)
        {
            stack.Push(split[letter]);
            queue.Enqueue(split[letter]);
        }
        
        for(int i = 0; i < split.length; i++)
        {
            System.out.print(stack.Peek());
            stack.Pop();
        }
		
		System.out.println("\n");
        
        for(int i = 0; i < split.length; i++)
        {
            queue.First();
            System.out.println(queue.GetValue());
            queue.Dequeue();
        }
            
    }
}