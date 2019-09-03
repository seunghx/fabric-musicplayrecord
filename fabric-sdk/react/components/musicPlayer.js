import React from 'react'
import {playMusic} from '../util/api'

const MusicImage = ({image}) => 
  <img className="card-img-top music-player-image" src={image} alt="" />
  
const MusicName = ({title}) => 
  <div className="container" >
      <h5 className="card-title txt_line center music-player">
          {title}
      </h5>
  </div>

const MusicSinger = ({singers}) =>
  <div className="container">
      <p className="card-text text-secondary txt_line music-player">
          {singers.join(', ')}
      </p>
  </div>

const MusicAlbum = ({album}) =>
  <div className="container music-player">
      <p className="card-text text-secondary txt_line music-player">
          {album}
      </p>
  </div>

const MusicPlayButton = ({music}) => {

  const onPlayMusic = e => {
      e.preventDefault()

      let musicInfo = {
          musicId : music.id,
          title : music.title,
          singers : music.singers,
          album : music.album
      }

      playMusic(musicInfo, res => console.log(res), err => console.log(err))
  }

  return(
      <div className="container">
          <button className="btn" onClick={onPlayMusic}>
              <img className="btn-img" src="/static/image/play.png" />
          </button> 
      </div>
  )
}

const MusicPlayer = ({music}) => 
    <div className="col-lg-3 col-md-4 col-sm-6 mb-4">
      <div className="card h-100">
        <MusicImage image={music.image}/>
        <div className="card-body center">
          <MusicName  title={music.title} />
          <MusicSinger singers={music.singers} />
          <MusicAlbum album={music.album} />
          <MusicPlayButton music={music} />
        </div>
      </div>
    </div>

export default MusicPlayer

