package application;

public class freqData implements Comparable<freqData>{
     int freq;
     char ch;
     
	public freqData(int freq, char ch) {
		super();
		this.freq = freq;
		this.ch = ch;
	}
	
	public freqData() {
	    super();
	    this.freq = 0; // Initialize frequency to 0
	    this.ch = '\0'; // Initialize character to null character
	}

	@Override
	public int compareTo(freqData other) {
	    // Compare based on frequency
	    return Integer.compare(this.freq, other.freq);
	}

     
     

   
}
