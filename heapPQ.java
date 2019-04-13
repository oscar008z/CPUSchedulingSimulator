
public class heapPQ<E> {
	private Object[] arrayHeap;
	private int size;
	private int capacity;
	private int front;
	private int rear;
	
	
	public heapPQ() {
		this.arrayHeap = new Object[6];
		this.size = 0;
		this.capacity = 5;
		this.front = 1;
		this.rear = 1;
	}
	
	public heapPQ(int capacity) {
		this.arrayHeap = new Object[capacity+1];
		this.size = 0;
		this.capacity = capacity;
		front = 1;
		rear = 1;
	}
	
	public Object[] getArrayHeap() {
		return arrayHeap;
	}

	public void setArrayHeap(Object[] arrayHeap) {
		this.arrayHeap = arrayHeap;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public void resize() {
		if((double) this.size/this.capacity>0.75) {
			Object[] tempArr = new Object[this.capacity*2];
			for(int i=0; i< this.rear; i++) {
				tempArr[i] = this.arrayHeap[i];
			}
			this.arrayHeap = tempArr;
			this.capacity = this.capacity*2;
		}
		else if((double) this.size/this.capacity<0.25 && this.capacity/2 >5) {
			Object[] tempArr = new Object[this.capacity/2];
			for(int i=0; i< this.rear; i++) {
				tempArr[i] = this.arrayHeap[i];
			}
			this.arrayHeap = tempArr;
			this.capacity = this.capacity/2;
		}else if((double) this.size/this.capacity<0.25 && this.capacity/2 >5) {
			Object[] tempArr = new Object[this.capacity/2];
			for(int i=0; i< this.rear; i++) {
				tempArr[i] = this.arrayHeap[i];
			}
			this.arrayHeap = tempArr;
			this.capacity = this.capacity/2;
		}
	}
	
	public void heapfy(Object[] arr, int rootIdx, int size) {
		int leftIdx = 2*rootIdx;
		int rightIdx = 2*rootIdx+1;
		int smallestIdx = rootIdx;
		if(arr!=null) {
			if(arr[1].getClass().getName().equals("Job")) {
				E eLeft;
				E eRight;
				E eRoot = (E) arr[rootIdx];
				
				if(leftIdx<=size && rightIdx<=size) {
					eLeft = (E) arr[leftIdx];
					eRight = (E) arr[rightIdx];
					if(comparator(eLeft,eRight)>0 && comparator(eLeft,eRoot)>0) {
						smallestIdx = leftIdx;
						//System.out.println("leftchild is higher: " + eLeft.toString());
						//System.out.println("root: " + eRoot.toString());
					}
					else if(comparator(eRight,eLeft)>0 && comparator(eRight,eRoot)>0) {
						smallestIdx = rightIdx;
						//System.out.println("leftchild is higher: " + eLeft.toString());
						//System.out.println("root: " + eRoot.toString());
					}
				}
				else if(leftIdx<=size && rightIdx>size) {
					eLeft = (E) arr[leftIdx];
					if(comparator(eLeft,eRoot)>0) {
						smallestIdx = leftIdx;
						//System.out.println("leftchild is higher: " + eLeft.toString());
						//System.out.println("root: " + eRoot.toString());
					}
				}
				else if(rightIdx<=size && leftIdx>size) {
					eRight = (E) arr[rightIdx];
					if(comparator(eRight,eRoot)>0) {
						smallestIdx = rightIdx;
						//System.out.println("rightchild is higher: " + eRight.toString());
						//System.out.println("root: " + eRoot.toString());
					}
						
				}
					
				if(smallestIdx!=rootIdx) {
					//System.out.println("smallestIdx: " + smallestIdx + "rootIdx: "+ rootIdx);
					swap(rootIdx, smallestIdx);
					heapfy(arr, smallestIdx, size);
				}
			}
			else {
				System.out.println("This type can not be dealt yet.");
				return;
			}
		}
	}
	
	public void buildHeap(Object[] arr, int size) {
		for(int i=size/2; i>=1; i--) {
			this.heapfy(arr, i, size);
		}			
	}
	
	public void add(E e) {
		Object o = (Object) e;
		if(this.size==0) {
			this.arrayHeap[this.front] = o;
			this.size++;
			this.rear++;
			this.resize();
		}
		else {
			if(this.arrayHeap[this.front].getClass().getName().equals("Job")) {
				int lastIdx = this.rear;
				this.arrayHeap[lastIdx] = o;
				this.size++;
				this.rear++;
				this.resize();
				int parentIdx = (lastIdx%2==0)?(lastIdx/2):((lastIdx-1)/2);
				while(parentIdx>=this.front) {
					E child = (E) this.arrayHeap[lastIdx];
					E parent = (E) this.arrayHeap[parentIdx];
					if(this.comparator(child, parent)>0) {
						this.swap(lastIdx,parentIdx);
					}
					lastIdx = parentIdx;
					parentIdx = (lastIdx%2==0)?(lastIdx/2):((lastIdx-1)/2);
				}
			}
			else {
				System.out.println("This type can not be dealt yet.");
				return;
			}
		}
	}
	
	public E removeMin() {
		E e = null;
		if(this.isEmpty()==false) {
			e = (E) this.arrayHeap[this.front];
			if(e.getClass().getName().equals("Job")) {
				if(this.size==1) {
					this.size--;
					this.rear--;
				}
				else {
					this.swap(this.front, (this.rear-1));
					this.arrayHeap[this.rear-1]=null;
					this.size--;
					this.rear--;
					
					int parentIdx = this.front;
					int leftChildIdx = 2*parentIdx;
					int rightChildIdx = 2*parentIdx+1;
					E parentE = (E) this.arrayHeap[this.front];
					int smallestIdx = parentIdx;
					boolean flag = false;
					while(flag==false && parentIdx<=this.size) {
						if(leftChildIdx<=this.size && rightChildIdx<=this.size) {
							E leftE = (E) this.arrayHeap[leftChildIdx];
							E rightE = (E) this.arrayHeap[rightChildIdx];
							if(this.comparator(leftE, rightE)>0 && this.comparator(leftE, parentE)>0) {
								smallestIdx = leftChildIdx;
							}
							else if(this.comparator(rightE, leftE)>0 && this.comparator(rightE, parentE)>0) {
								smallestIdx = rightChildIdx;
							}
						}
						else if(leftChildIdx<=this.size) {
							E leftE = (E) this.arrayHeap[leftChildIdx];
							if(this.comparator(leftE, parentE)>0) {
								smallestIdx = leftChildIdx;
							}
						}
						else if(rightChildIdx<=this.size) {
							E rightE = (E) this.arrayHeap[rightChildIdx];
							if(this.comparator(rightE, parentE)>0) {
								smallestIdx = rightChildIdx;
							}
						}
						
						if(smallestIdx!=parentIdx) {
							swap(smallestIdx,parentIdx);
							parentIdx = smallestIdx;
							leftChildIdx = 2*parentIdx;
							rightChildIdx = 2*parentIdx+1;
						}
						else {
							flag = true;
						}
						
						//System.out.println(parentIdx);
					}
				}
				this.resize();
			}
			else {
				System.out.println("This type can not be dealt yet.");
			}
		}
		return e;
	}
	
	public boolean isEmpty() {
		boolean flag = false;
		if(this.size==0)
			flag = true;
		return flag;
	}
	
	public void swap(int index1, int index2) {
		E tempObj = (E) this.arrayHeap[index1];
		this.arrayHeap[index1] = this.arrayHeap[index2];
		this.arrayHeap[index2] = tempObj;
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
}
