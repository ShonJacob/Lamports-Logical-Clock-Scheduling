import java.util.*;
public class LamportLogicalClock
{
	public static void main(String args[])
	{
		//p->number of processes
		//e[p]->number of events of process p
		//highest->highest number of event
		//r->number of relations
		//originp & origine -> the origin process and event of the relation
		//terminatorp & terminatore -> the terminal process and event of the relation    Eg:e11 -> e23
		//timestamp[p][]-> a jagged array holding processes and the corresponding number of events
		
		int p;	
		Scanner s=new Scanner(System.in);
		//INPUTTING THE NUMBER OF PROCESSES AND EVENTS
		System.out.println("Enter the number of processes");
		p=s.nextInt();
		int e[]=new int[p];
		System.out.println("Enter the number of events in each process");
		for(int i=0;i<p;i++)
		{
			e[i]=s.nextInt();
		}
		int highest;
		System.out.println("Enter the highest number of events");
		highest=s.nextInt();
		System.out.println("Enter the number of relationships");
		int r;
		r=s.nextInt();
		//ARRAYS FOR HOLDING RELATIONSHIP PROCESSES AND EVENTS
		int originp[]=new int[r];
		int origine[]=new int[r];
		int terminatorp[]=new int[r];
		int terminatore[]=new int[r];
		//INPUTTING THE RELATIONS
		//if e1->e2 , e1 is causally related to e2, i.e., the output of e2 is affected by output of e1
		System.out.println("Enter the relationships in order,eg;1 2 , 3 1");
		for(int i=0;i<r;i++)
		{
			System.out.println("Enter the process ");
			originp[i]=s.nextInt();
			System.out.println("Enter the event ");
			origine[i]=s.nextInt();
			System.out.println("is related to");
			System.out.println("Enter the process ");
			terminatorp[i]=s.nextInt();
			System.out.println("Enter the event ");
			terminatore[i]=s.nextInt();
		}
		//INITIALIZING JAGGED ARRAY
		int timestamp[][]=new int[p][];
		for(int i=0;i<p;i++)
		{
			timestamp[i]=new int[e[i]];
					
		}
	
		//CREATING THE INITIAL TIMESTAMP VALUES	
		for(int i=0;i<p;i++)
		{
			for(int j=0;j<e[i];j++)
			{
				timestamp[i][j]++;
			}
		}
		
		//WE PUT A LOOP HERE BECAUSE IT NEEDS TO BE RESCHEDULED MULTIPLE TIMES AFTER VALUES ARE UPDATED
		for(int g=0;g<p*highest;g++)
		{	
		//SCHEDULING WHEN RELATIONS ARE ACROSS PROCESSES
		for(int i=0;i<r;i++)
		{
			if(timestamp[originp[i]-1][origine[i]-1]>=timestamp[terminatorp[i]-1][terminatore[i]-1])
			{
				timestamp[terminatorp[i]-1][terminatore[i]-1]=timestamp[originp[i]-1][origine[i]-1]+1;
			}
		}
		//SCHEDULING WHEN RELATIONS ARE IN THE SAME PROCESS,i.e., SERIAL/CAUSALLY RELATED EVENTS IN SAME PROCESS
		for(int i=0;i<p;i++)
		{
			for(int j=0;j<e[i];j++)
			{
				if(j!=e[i]-1)
				{
					if(timestamp[i][j]>=timestamp[i][j+1])
					{
						timestamp[i][j+1]=timestamp[i][j]+1;
					}
				}
				else
					if(timestamp[i][j]<=timestamp[i][j-1])
					{
						timestamp[i][j]=timestamp[i][j-1]+1;
					}
			}
		}
	}
		//DISPLAYING THE FINAL SCHEDULE
		for(int i=0;i<p;i++)
		{System.out.println("PROCESS : "+(i+1) );
			for(int j=0;j<e[i];j++)
			{
				System.out.print("\t"+timestamp[i][j]);
			}
			System.out.println();
		}
		s.close();
	}
}
/*SAMPLE INPUT AND OUTPUT
Enter the number of processes
4
Enter the number of events in each process
4
4
2
3
Enter the highest number of events
4
Enter the number of relationships
3
Enter the relationships in order,eg;1 2 , 3 1
Enter the process 
4
Enter the event 
2
is related to
Enter the process 
3
Enter the event 
1
Enter the process 
3
Enter the event 
1
is related to
Enter the process 
2
Enter the event 
2
Enter the process 
2
Enter the event 
2
is related to
Enter the process 
1
Enter the event 
1
PROCESS : 1
	5	6	7	8
PROCESS : 2
	1	4	5	6
PROCESS : 3
	3	4
PROCESS : 4
	1	2	3

*/
