import React, { useState } from 'react';
import jsQR from 'jsqr';

const UploadQRCode = ({ onQrCode }) => {
  const [selectedFile, setSelectedFile] = useState();
  const [selectedFileName, setSelectedFileName] = useState('');
  const [uploaded, setUploaded] = useState(false);
  const [qrCodeData, setQrCodeData] = useState(null);

  const fileSelectedHandler = (event) => {
    setSelectedFile(event.target.files[0]);
    setSelectedFileName(event.target.files[0].name);
  };

  const fileUploadHandler = () => {
    const formData = new FormData();
    formData.append('file', selectedFile, selectedFile.name);
    setUploaded(true);
    // Make a request to your server here. This is just a dummy example.

    // Read QR code data
    const reader = new FileReader();
    reader.onload = function () {
      const image = new Image();
      image.src = reader.result as string;
      image.onload = function () {
        const canvas = document.createElement('canvas');
        const context = canvas.getContext('2d');
        canvas.width = image.width;
        canvas.height = image.height;
        context.drawImage(image, 0, 0, image.width, image.height);
        const imageData = context.getImageData(
          0,
          0,
          canvas.width,
          canvas.height,
        );
        const qrCode = jsQR(imageData.data, imageData.width, imageData.height);
        if (qrCode) {
          setQrCodeData(qrCode.data);
          onQrCode(qrCode.data);
        }
      };
    };
    reader.readAsDataURL(selectedFile);
  };

  return (
    <div>
      <input
        type="file"
        accept=".png, .jpg, .jpeg"
        onChange={fileSelectedHandler}
      />
      <button onClick={fileUploadHandler}>Upload</button>
      {selectedFileName && <p>{selectedFileName}</p>}
      {qrCodeData && <p>QR Code Data: {qrCodeData}</p>}
    </div>
  );
};

export default UploadQRCode;
