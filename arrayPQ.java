
public class arrayPQ<E> {
	private Object[] arrayPQ;
	private int arrLength;
	private int front;
	private int rear;
	private int size;
	
	public Object[] getArrayPQ() {
		return arrayPQ;
	}
	public void setArrayPQ(Object[] arrayPQ) {
		this.arrayPQ = arrayPQ;
	}
	public int getArrLength() {
		return arrLength;
	}
	public void setArrLength(int arrLength) {
		this.arrLength = arrLength;
	}
	public int getFront() {
		return front;
	}
	public void setFront(int front) {
		this.front = front;
	}
	public int getRear() {
		return rear;
	}
	public void setRear(int rear) {
		this.rear = rear;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public arrayPQ() {
		this.arrayPQ = null;
		this.arrLength = 0;
		this.front = 0;
		this.rear = 0;
		this.size = 0;
	}
	public arrayPQ(int PQlength) {
		this.arrLength = PQlength+1;	//循环数组实现队列，数组容量需要比实际元素数量多1
		this.arrayPQ = new Object[this.arrLength];
		this.front = 0;
		this.rear = 0;
		this.size = 0;
	}
	
	public void add(E element) {
		E curItem = element;
		if(this.isEmpty()==true) {
			this.setFront(0);
			this.setRear(0);
			this.arrayPQ[this.front] = element;
			this.rear = (this.rear+1) % this.arrLength;
			this.size++;
		}
		else {
			for(int j=this.front; j<(this.front+this.size); j++) {
				E e1 = (E) this.arrayPQ[j%this.arrLength];
				if(comparator(e1,curItem)<0) {
					E temp = (E) this.arrayPQ[j%this.arrLength];
					this.arrayPQ[j%this.arrLength] = curItem;
					curItem = temp;
				}
			}
			this.arrayPQ[this.rear] = curItem;
			this.rear = (this.rear+1) % this.arrLength;
			this.size++;
		}
		
	}
	
	public E removeMin() {
			if(this.isEmpty()==false) {
				//System.out.println("the first element is :" + this.arrayPQ[this.front].toString());
				E minElement = (E) this.arrayPQ[this.front];
				this.arrayPQ[this.front] = null;
				this.front = (this.front+1) % this.arrLength;
				this.size--;
				return minElement;
			}
			else {
				return null;
			}
	}
	
	public void swap(int index1, int index2) {
		E tempObj = (E) this.arrayPQ[index1];
		this.arrayPQ[index1] = this.arrayPQ[index2];
		this.arrayPQ[index2] = tempObj;
	}
	
	public int comparator(E element1, E element2) {
		int res = -1;
		Job e1 = (Job) element1;
		Job e2 = (Job) element2;
		if(e1.getFinalPriority()<e2.getFinalPriority()) {
			res = 1;
		}
		else if(e1.getFinalPriority()==e2.getFinalPriority()) {
			if(e1.getLastExecutTime()<e2.getLastExecutTime()) {
				res = 1;
			}
			else if(e1.getLastExecutTime()==e2.getLastExecutTime()) {
				if(e1.getEntryTime()<e2.getEntryTime()) {
					res = 1;
				}
			}
		}
		return res;
	}
	
	public boolean isEmpty() {
		boolean flag = false;
		if(this.size==0)
			flag = true;
		return flag;
	}
}
