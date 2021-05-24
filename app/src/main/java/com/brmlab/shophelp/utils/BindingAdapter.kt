package com.brmlab.shophelp.utils

import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.brmlab.shophelp.R
import com.brmlab.shophelp.model.Cast
import com.brmlab.shophelp.ui.fragment.ListFragmentDirections
import com.brmlab.shophelp.model.PopularResult
import com.brmlab.shophelp.model.PersonResult
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class BindingAdapter {
    companion object{

        @BindingAdapter("android:adultChecker")
        @JvmStatic
        fun adultChecker(view: TextView, model: PopularResult){
            if (model.adult){
                view.text = "18+"
            }
            else{
                view.text = "3+"
            }
        }

        @BindingAdapter("android:urlToImage")
        @JvmStatic
        fun urlToImage(view: ImageView, url: String){
            val downloadUrl = "https://image.tmdb.org/t/p/w500/$url"
            Picasso.with(view.context).load(downloadUrl).into(view)
        }
        @BindingAdapter("android:urlToCircleImage")
        @JvmStatic
        fun urlToCircleImage(view: CircleImageView, url: PersonResult){
            val downloadUrl = "https://image.tmdb.org/t/p/w500/${url.profile_path}"
            Picasso.with(view.context).load(downloadUrl).into(view)
        }
        @BindingAdapter("android:urlCastToCircleImage")
        @JvmStatic
        fun urlCastToCircleImage(view: CircleImageView, url: Cast){
            val downloadUrl = "https://image.tmdb.org/t/p/w500/${url.profile_path}"
            Picasso.with(view.context).load(downloadUrl).into(view)
        }
        @BindingAdapter("android:listToMovie")
        @JvmStatic
        fun listToMovie(view: LinearLayout, model: PopularResult){
            view.setOnClickListener {
                Log.d("LOG_TAG", model.id.toString())
                view.findNavController().navigate(ListFragmentDirections.actionListFragmentToShopFragment(
                        model
                ))
            }
        }

        @BindingAdapter("android:rateAndVote")
        @JvmStatic
        fun rateAndVote(view:TextView, model: PopularResult){
            view.text = "Оценка: ${model.vote_average} Голосов: ${model.vote_count}"
        }

        @BindingAdapter("android:personNameDivider")
        @JvmStatic
        fun personNameDivider(view: TextView, model: String){
            val list = model.split(" ")
            view.text = "${list[0]}\n${list[1]}"
        }
    }
}