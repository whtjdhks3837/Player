package test.exoplayer

import baejang.dynamic_media_view.data.Media
import baejang.dynamic_media_view.data.PlayList

const val hlsVideoUrl1 =
    "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"
const val hlsVideoUrl2 = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8"
const val hlsVideoUrl3 = "http://www.streambox.fr/playlists/test_001/stream.m3u8" // 404 video
const val hlsVideoUrl4 = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8" // 404 video
const val hlsVideoUrl5 = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8"

const val introUrl1 = "https://cdn-ani.smartstudy.co.kr/intro/ko/5003.mp4"
const val introUrl2 = "https://cdn-ani.smartstudy.co.kr/intro/ko/5002.mp4"
const val introAsset = "asset:///intro.mp4"

val playlist = PlayList(
    listOf(
        listOf(
            Media("video1", introAsset, "mp4"),
            Media("video2", introUrl2, "mp4")
        ),
        listOf(
            Media("video4", introUrl1, "mp4"),
            Media("video11", hlsVideoUrl5, "m3u8")
        )
    )
)