import java.util.*;

// Connection Class
// TCP Connections from host to remote.
// 28 Sep -- initial version. 


class Connection 
{
   String offset;		// offset in memory
   String localAddr;	// local interface address and port.
   String remoteAddr;	// remote address and port.
   String pid;			// process identifier.
	
   Connection(String offset, String local, String remote, String pid)	// constructor
   {
      this.offset = offset;
      this.localAddr = local;
      this.remoteAddr = remote;
      this.pid = pid;
   }
	
   Connection(ArrayList<String> theConn)
   {
      System.out.println("new connection with " + theConn.size() + " elements, " + theConn.toString());
   
      this.offset 	= theConn.get(0);
      this.localAddr 	= theConn.get(1);
      this.remoteAddr	= theConn.get(2);
      this.pid 		= theConn.get(3);
   }

   public String getOffset()
   {
      return this.offset;
   }
   public String getLocal()
   {
      return this.localAddr;
   }
   public String getRemote()
   {
      return this.remoteAddr;
   }
   public String getPid()
   {
      return this.pid;
   }
	
   @Override public String toString()
   {
      return	"Process " + this.pid + 
         	" has Connection from " + this.remoteAddr + 
         	" to local " + this.localAddr + 
         	" @ " + this.offset;
   }

}