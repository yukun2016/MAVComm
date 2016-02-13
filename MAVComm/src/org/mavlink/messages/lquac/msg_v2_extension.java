/**
 * Generated class : msg_v2_extension
 * DO NOT MODIFY!
 **/
package org.mavlink.messages.lquac;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.mavlink.IMAVLinkCRC;
import org.mavlink.MAVLinkCRC;
import org.mavlink.messages.MAVLinkMessage;
/**
 * Class msg_v2_extension
 * Message implementing parts of the V2 payload specs in V1 frames for transitional support.
 **/
public class msg_v2_extension extends MAVLinkMessage {
  public static final int MAVLINK_MSG_ID_V2_EXTENSION = 248;
  private static final long serialVersionUID = MAVLINK_MSG_ID_V2_EXTENSION;
  public msg_v2_extension() {
    this(1,1);
}
  public msg_v2_extension(int sysId, int componentId) {
    messageType = MAVLINK_MSG_ID_V2_EXTENSION;
    this.sysId = sysId;
    this.componentId = componentId;
    length = 254;
}

  /**
   * A code that identifies the software component that understands this message (analogous to usb device classes or mime type strings).  If this code is less than 32768, it is considered a 'registered' protocol extension and the corresponding entry should be added to https://github.com/mavlink/mavlink/extension-message-ids.xml.  Software creators can register blocks of message IDs as needed (useful for GCS specific metadata, etc...). Message_types greater than 32767 are considered local experiments and should not be checked in to any widely distributed codebase.
   */
  public int message_type;
  /**
   * Network ID (0 for broadcast)
   */
  public int target_network;
  /**
   * System ID (0 for broadcast)
   */
  public int target_system;
  /**
   * Component ID (0 for broadcast)
   */
  public int target_component;
  /**
   * Variable length payload. The length is defined by the remaining message length when subtracting the header and other fields.  The entire content of this block is opaque unless you understand any the encoding message_type.  The particular encoding used can be extension specific and might not always be documented as part of the mavlink specification.
   */
  public int[] payload = new int[249];
/**
 * Decode message with raw data
 */
public void decode(ByteBuffer dis) throws IOException {
  message_type = (int)dis.getShort()&0x00FFFF;
  target_network = (int)dis.get()&0x00FF;
  target_system = (int)dis.get()&0x00FF;
  target_component = (int)dis.get()&0x00FF;
  for (int i=0; i<249; i++) {
    payload[i] = (int)dis.get()&0x00FF;
  }
}
/**
 * Encode message with raw data and other informations
 */
public byte[] encode() throws IOException {
  byte[] buffer = new byte[8+254];
   ByteBuffer dos = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
  dos.put((byte)0xFE);
  dos.put((byte)(length & 0x00FF));
  dos.put((byte)(sequence & 0x00FF));
  dos.put((byte)(sysId & 0x00FF));
  dos.put((byte)(componentId & 0x00FF));
  dos.put((byte)(messageType & 0x00FF));
  dos.putShort((short)(message_type&0x00FFFF));
  dos.put((byte)(target_network&0x00FF));
  dos.put((byte)(target_system&0x00FF));
  dos.put((byte)(target_component&0x00FF));
  for (int i=0; i<249; i++) {
    dos.put((byte)(payload[i]&0x00FF));
  }
  int crc = MAVLinkCRC.crc_calculate_encode(buffer, 254);
  crc = MAVLinkCRC.crc_accumulate((byte) IMAVLinkCRC.MAVLINK_MESSAGE_CRCS[messageType], crc);
  byte crcl = (byte) (crc & 0x00FF);
  byte crch = (byte) ((crc >> 8) & 0x00FF);
  buffer[260] = crcl;
  buffer[261] = crch;
  return buffer;
}
public String toString() {
return "MAVLINK_MSG_ID_V2_EXTENSION : " +   "  message_type="+message_type+  "  target_network="+target_network+  "  target_system="+target_system+  "  target_component="+target_component+  "  payload="+payload;}
}
