package com.brimzi.app.filetransferpc;

import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;
import org.alljoyn.bus.annotation.Secure;

@BusInterface(name = "com.brimzi.app.filetransferpc")
@Secure
public interface TransferSignalInterface {
	@BusSignal
	public void currentFiles(String jsonStringFiles);
	
	@BusSignal
	public void filesToUpload(String sessionId,String jsonStringFiles);
	
	@BusSignal
	public void transferFilePiece(String syncSessionId,String filePath,int fileSize,int partNo,int pieceSize,byte[] data);
	
}
