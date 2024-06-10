import React, { useState } from 'react';
import { Button } from 'antd';
import jsQR from 'jsqr';

const UploadQRCode = ({ onQrCode, style }) => {
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
    <div style={style}>
      <label
        htmlFor="fileUpload"
        className="customFileSelect"
        style={{
          display: 'inline-block',
          padding: '10px 20px',
          backgroundColor: '#4CAF50',
          color: 'white',
          textAlign: 'center',
          textDecoration: 'none',
          fontSize: '16px',
          margin: '4px 2px',
          cursor: 'pointer',
          border: 'none',
          borderRadius: '4px',
          transitionDuration: '0.4s',
        }}
      >
        Select QR Code
      </label>
      <input
        id="fileUpload"
        type="file"
        accept=".png, .jpg, .jpeg"
        onChange={fileSelectedHandler}
        style={{ display: 'none' }}
      />
      <label
        className="customFileUpload"
        style={{
          display: 'inline-block',
          padding: '10px 20px',
          backgroundColor: '#4CAF50',
          color: 'white',
          textAlign: 'center',
          textDecoration: 'none',
          fontSize: '16px',
          margin: '4px 2px',
          cursor: 'pointer',
          border: 'none',
          borderRadius: '4px',
          transitionDuration: '0.4s',
        }}
        onClick={fileUploadHandler}
      >
        Upload
      </label>
      {selectedFileName && <p>{selectedFileName}</p>}
    </div>
  );
};

export default UploadQRCode;
