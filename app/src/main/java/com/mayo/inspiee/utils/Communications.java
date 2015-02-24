package com.mayo.inspiee.utils;

public class Communications {
	public interface FinishActivity{
		public void finishActivity();
	}
	
	public interface MoreAboutHim{
		public void moreAboutHim(int position);
	}

    public interface ShowTimerDialog{
        public void showTimerDialog(boolean isAm);
    }

    public interface ShowPersonalities{
        public void showPersonalities();
    }
}

