import React, {useState} from 'react';



const VideoFrame = ({url}: { url: string }) => {
    const [iFrameHeight, setIFrameHeight] = useState("0px")

    return (
        <>
            <iframe ref="iframe" scrolling="yes" frameBorder="0"
                    style={{width: '100%', height: iFrameHeight, overflow: 'visible'}}
                    onLoad={() => {//iframe高度不超过content的高度即可
                        let h = document.documentElement.clientHeight - 20;
                        setIFrameHeight(h + "px")
                    }}
                    src={url}
            />
        </>
    )
}

export default VideoFrame;