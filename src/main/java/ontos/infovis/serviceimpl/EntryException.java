package ontos.infovis.serviceimpl;

public class EntryException extends Exception {
	private static final long serialVersionUID = -8063961387390874313L;
	
	static public class EntryAlreadyExistsException extends EntryException {
		private static final long serialVersionUID = -6588590524364612839L;
	}
	static public class EntryNotFoundException extends EntryException {
		private static final long serialVersionUID = -6964314239221998251L;
	}
	static public class EntryStillUsedException extends EntryException {
		private static final long serialVersionUID = 1749559594107385991L;		
	}
}
